import styled from 'styled-components';
import DefaultButtons from '@components/common/DefaultButtons';

const TestWrapper = styled.div`
	display: flex;
	flex-direction: column;
	gap: 12px;
`;

const Jignonne = () => {
	return (
		<TestWrapper>
			<DefaultButtons color="main"> Button</DefaultButtons>
			<DefaultButtons color="background"> Button</DefaultButtons>
			<DefaultButtons color="success"> Button</DefaultButtons>
			<DefaultButtons color="info"> Button</DefaultButtons>
			<DefaultButtons color="point"> Button</DefaultButtons>
			<DefaultButtons color="login"> Button</DefaultButtons>
			<DefaultButtons color="danger"> Button</DefaultButtons>
		</TestWrapper>
	);
};

export default Jignonne;
