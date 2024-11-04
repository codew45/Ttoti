import MainContainer from '@components/main/MainContainer';
import styled from 'styled-components';

const MainPageWrapper = styled.div`
	position: relative; // 절대 위치 지정
	width: 100vw;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
`;

const MainPage = () => {
	return (
		<MainPageWrapper>
			<MainContainer />
		</MainPageWrapper>
	);
};

export default MainPage;
