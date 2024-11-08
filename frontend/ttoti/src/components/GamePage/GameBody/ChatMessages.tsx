// ChatMessages.tsx
import React, { useEffect, useRef } from 'react';
import styled from 'styled-components';

interface ChatMessagesProps {
  target: 'manitto' | 'maniti';
  messages: { sendTime: string; senderId: 'manitto' | 'maniti'; content: string }[];
}

const MessageContainer = styled.div`
  display: flex;
  flex-direction: column;
  padding: 16px;
  overflow-y: auto;
  flex: 1;
`;

const MessageBox = styled.div<{ $isOwnMessage: boolean }>`
  display: flex;
  flex-direction: row;
  justify-content: ${({ $isOwnMessage }) => ($isOwnMessage ? 'flex-end' : 'flex-start')};
  margin-bottom: 10px;
  gap: 5px;
`;

const TimeStamp = styled.div`
  margin-top: 20px;
  font-size: 12px;
  height: 10px;
`;

const MessageBubble = styled.div<{ $isOwnMessage: boolean }>`
  position: relative;
  font-family: 'SB 어그로';
  font-weight: 300;
  background-color: white;
  color: #333;
  padding: 10px;
  border-radius: 15px;
  font-size: 16px;
  max-width: 70%;
  min-width: 40px;
  word-wrap: break-word;
  white-space: pre-wrap;
  margin-bottom: 10px;

  /* 말풍선 꼬리 */
  &::after {
    content: '';
    position: absolute;
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 13px 10px 0 10px;
    border-color: white transparent transparent transparent;
    transform: ${({ $isOwnMessage }) => ($isOwnMessage ? 'matrix(0, 1, -1, 0, 0, 0)' : 'matrix(0, -1, 1, 0, 0, 0)')};
    bottom: -3px;
    left: ${({ $isOwnMessage }) => ($isOwnMessage ? 'auto' : '0px')};
    right: ${({ $isOwnMessage }) => ($isOwnMessage ? '0' : 'auto')};
  }
`;

const ChatMessages: React.FC<ChatMessagesProps> = ({ target, messages }) => {
  const containerRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    // 메시지가 추가될 때마다 스크롤을 맨 아래로 이동
    if (containerRef.current) {
      containerRef.current.scrollTop = containerRef.current.scrollHeight;
    }
  }, [messages]);

  const formatTime = (sendTime: string) => {
    const date = new Date(sendTime);
    const hours = date.getHours();
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const period = hours >= 12 ? '오후' : '오전';
    const formattedHours = hours % 12 || 12;

    return `${period} ${formattedHours}:${minutes}`;
  };


  return (
    <MessageContainer ref={containerRef}>
      {messages.map((message, index) => (
        <MessageBox key={index} $isOwnMessage={message.senderId !== target}>
          <TimeStamp>
            {formatTime(message.sendTime)}
          </TimeStamp>
          <MessageBubble $isOwnMessage={message.senderId !== target}>
            {message.content}
          </MessageBubble>
        </MessageBox>
      ))}
    </MessageContainer>
  );
};

export default ChatMessages;
