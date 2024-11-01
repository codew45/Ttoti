// TabContent.tsx
import React, { useState } from 'react';
import styled, { useTheme } from 'styled-components';
import QuizContent from './QuizContent';
import ManitoContent from './ManitoContent';
import ManitiContent from './ManitiContent';

const ContentContainer = styled.div<{ $backgroundColor: string }>`
  width: 100%;
  height: 505px;
  background-color: ${({ $backgroundColor }) => $backgroundColor};
  border-top: 1px solid black;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
`;

interface TabContentProps {
  activeTab: string;
}

const TabContent: React.FC<TabContentProps> = ({ activeTab }) => {
  const theme = useTheme();
  const [page, setPage] = useState(0); // 상태를 TabContent에서 관리

  let backgroundColor;

  switch (activeTab) {
    case 'quiz':
      backgroundColor = theme.colors.quiz;
      break;
    case 'manito':
      backgroundColor = theme.colors.manitoChat;
      break;
    case 'maniti':
      backgroundColor = theme.colors.manitiChat;
      break;
    default:
      backgroundColor = '#FFFFFF';
  }

  // 페이지 변경 함수
  const togglePage = (direction: 'next' | 'prev') => {
    setPage((prevPage) => (direction === 'next' ? Math.min(prevPage + 1, 1) : Math.max(prevPage - 1, 0))); // 0: 마니또, 1: 마니띠
  };

  return (
    <ContentContainer $backgroundColor={backgroundColor}>
      {activeTab === 'quiz' && <QuizContent page={page} togglePage={togglePage} />} {/* props로 상태 전달 */}
      {activeTab === 'manito' && <ManitoContent />}
      {activeTab === 'maniti' && <ManitiContent />}
    </ContentContainer>
  );
};

export default TabContent;
