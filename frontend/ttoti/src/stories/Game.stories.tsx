// src/stories/Game.stories.tsx
import { Meta, StoryFn } from '@storybook/react';
import Game from '@components/GamePage/Game';
import { ThemeProvider } from 'styled-components';
import theme from '@styles/theme';

const meta: Meta<typeof Game> = {
  title: 'Game/Game',
  component: Game,
};

export default meta;

// 스토리 1: 기본 게임 화면
const Template: StoryFn = () => (
  <ThemeProvider theme={theme}>
    <Game />
  </ThemeProvider>
);

// 기본 예시
export const Default = Template.bind({});
