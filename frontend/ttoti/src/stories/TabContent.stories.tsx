// src/stories/TabContent.stories.tsx
import { Meta, StoryFn } from '@storybook/react';
import TabContent from '@components/GamePage/GameBody/TabContent';
import { ThemeProvider } from 'styled-components';
import theme from '@styles/theme';// 테마를 실제 경로에서 가져옵니다

const meta: Meta<typeof TabContent> = {
  title: 'Game/TabContent',
  component: TabContent,
};

export default meta;

// 스토리 1: Quiz 탭
const Template: StoryFn<{ activeTab: string }> = (args) => (
  <ThemeProvider theme={theme}>
    <TabContent {...args} />
  </ThemeProvider>
);

// Quiz 탭 예시
export const QuizTab = Template.bind({});
QuizTab.args = {
  activeTab: 'quiz',
};

// Manito 탭 예시
export const ManitoTab = Template.bind({});
ManitoTab.args = {
  activeTab: 'manito',
};

// Maniti 탭 예시
export const ManitiTab = Template.bind({});
ManitiTab.args = {
  activeTab: 'maniti',
};
