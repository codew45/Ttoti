// MyButton.stories.tsx - Storybook에 사용할 스토리 파일입니다.
import MyButton, { MyButtonProps } from '../components/Button/MyButton'; // MyButton 컴포넌트와 props 가져오기
import { Meta, StoryFn } from '@storybook/react';

// Storybook에서 MyButton 컴포넌트의 기본 설정을 정의합니다.
export default {
  title: 'MyComponents/MyButton', // 스토리의 그룹명과 컴포넌트 이름
  component: MyButton, // 사용될 컴포넌트 지정
  argTypes: { // 각 props에 대해 Storybook의 컨트롤 설정
    backgroundColor: { control: 'color' }, // 색상 선택 컨트롤
    label: { control: 'text' }, // 텍스트 입력 컨트롤
    onClick: { action: 'clicked' }, // 클릭 시 Storybook에서 액션 로깅
    size: {
      control: {
        type: 'select', // 드롭다운으로 선택할 수 있는 컨트롤
        options: ['small', 'medium', 'large'], // 버튼 크기 옵션 정의
      },
    },
  },
} as Meta;

// Template 변수에 컴포넌트를 렌더링할 함수 템플릿을 설정합니다.
const Template: StoryFn<MyButtonProps> = (args) => <MyButton {...args} />;

// Primary 버튼 스토리 생성
export const Primary = Template.bind({}); // Template을 복제하여 Primary 스토리로 생성
Primary.args = {
  label: 'Primary Button', // 기본 텍스트
  backgroundColor: '#007bff', // 기본 배경색
  size: 'medium', // 기본 크기
};

// Secondary 버튼 스토리 생성
export const Secondary = Template.bind({}); // Template을 복제하여 Secondary 스토리로 생성
Secondary.args = {
  label: 'Secondary Button', // 기본 텍스트
  backgroundColor: '#6c757d', // Secondary 버튼용 배경색
  size: 'medium', // 기본 크기
};