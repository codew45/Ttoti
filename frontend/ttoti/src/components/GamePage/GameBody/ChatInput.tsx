// ChatInput.tsx
import React, { useState } from 'react';
import styled, { useTheme } from 'styled-components';

const InputContainer = styled.div<{ $backgroundColor: string }>`
  width: 360px;
  height: 80px;
  display: flex;
  align-items: center;
  background-color: ${({ $backgroundColor }) => $backgroundColor};
  border-top: 3px solid black;
`;

const InputField = styled.input`
  flex: 1;
  padding: 8px;
  font-size: 16px;
  margin-left: 8px; 
  border: 2px solid #e1e9ef;
  border-radius: 15px;
  outline: none;
`;

const SendButton = styled.button`
  background: none;
  border: none;
  padding: 8px;
  cursor: pointer;
`;

interface ChatInputProps {
  onSendMessage: (message: string) => void;
}

const ChatInput: React.FC<ChatInputProps> = ({ onSendMessage }) => {
  const [message, setMessage] = useState('');
  const theme = useTheme();

  const handleSendMessage = () => {
    if (message.trim()) {
      onSendMessage(message);
      setMessage('');
    }
  };

  return (
    <InputContainer $backgroundColor={theme.colors.submain}>
      <InputField
        type="text"
        placeholder="메시지를 입력하세요"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        onKeyPress={(e) => e.key === 'Enter' && handleSendMessage()}
      />
      <SendButton onClick={handleSendMessage}>
        <img src="src/assets/icons/send_message.svg" alt="Send" />
      </SendButton>
    </InputContainer>
  );  
};

export default ChatInput;
