import styled from 'styled-components';
import LoginContainer from '@components/main/LoginContainer';

const LoginWrapper = styled.div`
	position: relative;
`;

const LoginPage = () => {
	return (
		<LoginWrapper>
			<LoginContainer />
		</LoginWrapper>
	);
};

export default LoginPage;
