// TabContent.tsx
import React from 'react';
import styled, { useTheme } from 'styled-components';

const ContentContainer = styled.div<{ backgroundColor: string }>`
  width: 100%;
  height: 505px; /* 필요에 따라 높이 조정 */
  background-color: ${({ backgroundColor }) => backgroundColor};
  border-top: 1px solid black;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white; /* 텍스트 색상 */
`;

interface TabContentProps {
  activeTab: string;
}

const TabContent: React.FC<TabContentProps> = ({ activeTab }) => {
  const theme = useTheme();
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
      backgroundColor = '#FFFFFF'; // 기본 배경색
  }

  return (
    <ContentContainer backgroundColor={backgroundColor}>
      {activeTab === 'quiz' && <div>퀴즈 내용</div>}
      {activeTab === 'manito' && <div>마니또 내용</div>}
      {activeTab === 'maniti' && <div>마니띠 내용</div>}
    </ContentContainer>
  );
};

export default TabContent;
