import styled from 'styled-components';
import DefaultButtons from '@components/common/DefaultButtons';
import GameButtons from '@components/common/GameButtons';

const TestWrapper = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	gap: 12px;
`;

const Jignonne = () => {
	return (
		<TestWrapper>
			<h1>기본 버튼</h1>
			<DefaultButtons color="main"> Button</DefaultButtons>
			<DefaultButtons color="background"> Button</DefaultButtons>
			<DefaultButtons color="success"> Button</DefaultButtons>
			<DefaultButtons color="info"> Button</DefaultButtons>
			<DefaultButtons color="point"> Button</DefaultButtons>
			<DefaultButtons color="login"> Button</DefaultButtons>
			<DefaultButtons color="danger"> Button</DefaultButtons>
			<h1>게임 버튼</h1>
			<GameButtons color="main">Button</GameButtons>
			<GameButtons color="success">Button</GameButtons>
			<GameButtons color="danger">Button</GameButtons>
		</TestWrapper>
	);
};

export default Jignonne;
