// Game.tsx
import { useState } from 'react';
import styled, { ThemeProvider } from 'styled-components';
import theme from '@styles/theme'; // 테마 파일 경로에 맞게 조정
import Tabs from './GameBody/Tabs';
import TabContent from './GameBody/TabContent';
import { QuizData } from "src/types/QuizTypes"; // QuizData 타입 import

interface GameProps {
  quizData: QuizData | null;
}

const GameWrapper = styled.div`
  bottom: 0px;
`;

const Game: React.FC<GameProps> = ({ quizData }) => {
  const [activeTab, setActiveTab] = useState('quiz'); // 초기 탭을 'quiz'로 설정

  return (
    <ThemeProvider theme={theme}>
      <GameWrapper>
        <Tabs activeTab={activeTab} onChangeTab={setActiveTab} />
        <TabContent activeTab={activeTab} quizData={quizData} />
      </GameWrapper>
    </ThemeProvider>
  );
};

export default Game;
