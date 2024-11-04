// src/stories/NotificationModal.stories.tsx
import { Meta, StoryFn } from '@storybook/react';
import NotificationModal from '@components/common/modals/NotificationModal';
import { ThemeProvider } from 'styled-components';
import theme from '@styles/theme';

const meta: Meta = {
  title: 'Modals/NotificationModal',
  component: NotificationModal,
  decorators: [(Story) => <ThemeProvider theme={theme}><Story /></ThemeProvider>],
};

export default meta;

// NotificationModal 컴포넌트 템플릿
const Template: StoryFn = () => <NotificationModal />;

// 기본 NotificationModal 예시
export const Default = Template.bind({});
Default.args = {};
