import styled from 'styled-components';
import LoginContainer from '@components/main/LoginContainer';

const LoginWrapper = styled.div`
	position: relative;
	width: 100vw;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
`;

const LoginPage = () => {
	return (
		<LoginWrapper>
			<LoginContainer />
		</LoginWrapper>
	);
};

export default LoginPage;
