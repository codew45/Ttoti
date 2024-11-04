// QuizBody.stories.tsx
import { Meta, StoryFn } from '@storybook/react'; // Meta와 Story import
import QuizBody from '../components/GamePage/GameBody/QuizBody';

// QuizBody의 Props 타입 정의
interface QuizBodyProps {
  page: number; // QuizBody에서 사용할 props 정의
}

// Storybook 메타데이터 설정
const meta: Meta<typeof QuizBody> = {
  title: 'Quiz/QuizBody',
  component: QuizBody,
};

export default meta;

// Template 생성
const Template: StoryFn<QuizBodyProps> = (args) => <QuizBody {...args} />;

// 각 스토리 정의
export const ManitoPage = Template.bind({});
ManitoPage.args = {
  page: 0, // isManito가 true인 경우
};

export const OtherPage = Template.bind({});
OtherPage.args = {
  page: 1, // isManito가 false인 경우
};
