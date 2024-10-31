// Tabs.tsx
import React from 'react';
import styled, { useTheme } from 'styled-components';
import PorcupineGif from '@assets/gamecloud/PorcupineGif.gif';
import RabbitGif from '@assets/gamecloud/RabbitGif.gif';
import OwlGif from '@assets/gamecloud/OwlGif.gif';

const TabContainer = styled.div`
  display: flex;
  justify-content: space-around;
  position: relative;
  margin-bottom: -1.76px;
`;

const TabWrapper = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const Tab = styled.button<{ color: string; isActive: boolean }>`
  width: 108px;
  background-color: ${({ color }) => color};
  font-family: 'SB어그로';
  font-weight: 300;
  font-size: 16px;
  color: white;
  border: 1px solid black;
  border-bottom: ${({ isActive }) => (isActive ? 'none' : '1px solid black')};
  border-top-left-radius: 12px;
  border-top-right-radius: 12px;
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.3s;
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1;
`;

const TabImage = styled.img`
  width: 60px;
  height: 60px;
  position: absolute;
  top: -60px; /* Tab 위로 배치 */
  z-index: 2;
`;

interface TabsProps {
  activeTab: string;
  onChangeTab: (tab: string) => void;
}

const Tabs: React.FC<TabsProps> = ({ activeTab, onChangeTab }) => {
  const theme = useTheme();

  return (
    <TabContainer>
      <TabWrapper>
        <TabImage src={PorcupineGif} alt="Porcupine" />
        <Tab
          onClick={() => onChangeTab('quiz')}
          color={theme.colors.quiz}
          isActive={activeTab === 'quiz'}
        >
          퀴즈
        </Tab>
      </TabWrapper>
      <TabWrapper>
        <TabImage src={RabbitGif} alt="Rabbit" />
        <Tab
          onClick={() => onChangeTab('manito')}
          color={theme.colors.manitoChat}
          isActive={activeTab === 'manito'}
        >
          마니또
        </Tab>
      </TabWrapper>
      <TabWrapper>
        <TabImage src={OwlGif} alt="Owl" />
        <Tab
          onClick={() => onChangeTab('maniti')}
          color={theme.colors.manitiChat}
          isActive={activeTab === 'maniti'}
        >
          마니띠
        </Tab>
      </TabWrapper>
    </TabContainer>
  );
};

export default Tabs;
