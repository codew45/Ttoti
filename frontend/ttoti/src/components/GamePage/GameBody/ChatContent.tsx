// ChatContent.tsx
import React, { useState, useEffect, useContext } from 'react';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';

import { WebSocketContext } from './TabContent';  // WebSocket 클라이언트를 관리하는 Context

import { RoomInfo } from 'src/types/RoomInfo';

interface ChatContentProps {
  target: 'manitto' | 'maniti';
  roomInfo: RoomInfo;
}

interface Message {
  senderId: 'manitto' | 'maniti';
  content: string;
}

const ChatContent: React.FC<ChatContentProps> = ({ target, roomInfo }) => {
  const [messages, setMessages] = useState<Message[]>([]);
  const stompClient = useContext(WebSocketContext);  // WebSocket 클라이언트

  const chatId = target === 'manitto'
    ? `${roomInfo.ttotiMatchInfo.myTittoId}`
    : `${roomInfo.ttotiMatchInfo.myTtotiId}`;

  useEffect(() => {
    if (stompClient) {
      // 구독 경로 설정
  
      // 메시지 구독
      const subscription = stompClient.subscribe(`/sub/sub-${chatId}`, (message) => {
        const newMessage = JSON.parse(message.body);
        console.log('Received message:', newMessage);
        setMessages((prevMessages) => [...prevMessages,  {
          senderId: newMessage.role,       // role을 senderId로 매핑
          content: newMessage.message,     // message를 content로 매핑
        }]);
      });
  
      // 컴포넌트 언마운트 시 구독 해제
      return () => {
        console.log(`Unsubscribing from: /sub/sub-${chatId}`);
        subscription.unsubscribe();
      };
    }
  }, [stompClient, target, roomInfo, chatId]);
  

  const sendMessage = (message: string) => {
    if (stompClient) {
      const role = target === 'manitto' ? 'maniti' : 'manitto';
      console.log(`Sending message to /pub/${role}/${chatId}:`, message);
      stompClient.publish({
        destination: `/pub/${role}/${chatId}`,
        body: JSON.stringify({ message: message }),
      });
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
      {/* 채팅 메시지 리스트 */}
      <ChatMessages target={target} messages={messages} />

      {/* 채팅 입력 필드 */}
      <ChatInput onSendMessage={sendMessage}/>
    </div>
  );
};

export default ChatContent;
