import MainContainer from '@components/main/MainContainer';
import styled from 'styled-components';

const MainPageWrapper = styled.div`
	position: relative;
`;

const MainPage = () => {
	return (
		<MainPageWrapper>
			<MainContainer />
		</MainPageWrapper>
	);
};

export default MainPage;
