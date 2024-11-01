import { useEffect, useState } from 'react';
import { Route, Routes, useLocation } from 'react-router-dom';
import styled from 'styled-components';

// 배경화면 import
import LoginBackground from '@assets/images/login.png';
import MainBackground from '@assets/images/main.png';
import MypageBackground from '@assets/images/mypage.png';

import CharacterSelect from '@pages/CharacterSelectPage';
import CreditPage from '@pages/CreditPage';
import GamePage from '@pages/GamePage';
import GameWaitingPage from '@pages/GameWaitingPage';
import LandingPage from '@pages/LandingPage';
import LoginPage from '@pages/LoginPage';
import MainPage from '@pages/MainPage';
import MyPage from '@pages/MyPage';
import RoomCreatePage from '@pages/RoomCreatePage';
import RoomListPage from '@pages/RoomListPage';

// Test Page import
import HoseaKim from '@pages/test/HoseaKim';
import Jignonne from '@pages/test/Jignonne';
import MooMoo from '@pages/test/MooMoo';

const Background = styled.div`
	position: absolute;
	// 임시 contain 설정
	background-size: contain;
	background-position: center;

	width: 360px;
	height: 800px;
	z-index: -1;
`;

const AppRouter = () => {
	const [backgroundImage, setBackgroundImage] = useState<string | null>(null);
	const location = useLocation();

	// 경로 변경 시 path -> pathname 확인 후 배경화면 지정

	useEffect(() => {
		const pathname = location.pathname.split('/')[1];
		if (pathname === 'login') {
			setBackgroundImage(LoginBackground);
		} else if (pathname === 'mypage') {
			setBackgroundImage(MypageBackground);
		} else {
			setBackgroundImage(MainBackground);
		}
	}, [location.pathname]);

	return (
		<>
			<Background style={{ backgroundImage: `url(${backgroundImage})` }} />
			<Routes>
				{/* 메인 라우트 */}
				<Route path="/" element={<MainPage />} />
				<Route path="/login" element={<LoginPage />} />
				<Route path="/room-create" element={<RoomCreatePage />} />
				<Route path="/room-list" element={<RoomListPage />} />
				<Route path="/game-waiting" element={<GameWaitingPage />} />
				<Route path="/game" element={<GamePage />} />
				<Route path="/character-select" element={<CharacterSelect />} />
				<Route path="/mypage" element={<MyPage />} />
				<Route path="/landing" element={<LandingPage />} />
				<Route path="/credit" element={<CreditPage />} />
				{/* 테스트 페이지 라우트  */}
				<Route path="/hoseakim" element={<HoseaKim />} />
				<Route path="/jignonne" element={<Jignonne />} />
				<Route path="/moomoo" element={<MooMoo />} />
			</Routes>
		</>
	);
};

export default AppRouter;
