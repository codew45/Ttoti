// ChatContent.tsx
import React, { useState } from 'react';
import ChatMessages from './ChatMessages';
import ChatInput from './ChatInput';

interface ChatContentProps {
  target: 'manito' | 'maniti';
}

interface Message {
  sender: 'manito' | 'maniti';
  content: string;
}

const ChatContent: React.FC<ChatContentProps> = ({ target }) => {
  const [messages, setMessages] = useState<Message[]>([]);

  const handleSendMessage = (message: string) => {
    setMessages([...messages, { sender: target, content: message }]);
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', height: '100%' }}>
      {/* 채팅 메시지 리스트 */}
      <ChatMessages target={target} messages={messages} />

      {/* 채팅 입력 필드 */}
      <ChatInput onSendMessage={handleSendMessage} />
    </div>
  );
};

export default ChatContent;
