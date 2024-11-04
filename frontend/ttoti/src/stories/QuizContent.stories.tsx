// QuizContent.stories.tsx
import { Meta, StoryFn } from '@storybook/react';
import QuizContent from '@components/GamePage/GameBody/QuizContent';

const meta: Meta<typeof QuizContent> = {
  title: 'Quiz/QuizContent',
  component: QuizContent,
};

export default meta;

interface QuizContentProps {
  page: number;
  togglePage: (direction: 'next' | 'prev') => void;
}

// Template 생성
const Template: StoryFn<QuizContentProps> = (args) => <QuizContent {...args} />;

// 각 스토리 정의
export const Default = Template.bind({});
Default.args = {
  page: 0,
  togglePage: (direction: 'next' | 'prev') => {
    // 페이지를 토글하는 간단한 로직
    console.log(`Toggle to ${direction}`);
  },
};

export const AnotherExample = Template.bind({});
AnotherExample.args = {
  page: 1,
  togglePage: (direction: 'next' | 'prev') => {
    // 페이지를 토글하는 간단한 로직
    console.log(`Toggle to ${direction}`);
  },
};
