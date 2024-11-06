import { useEffect, useState } from 'react';
import { Navigate, Route, Routes, useLocation } from 'react-router-dom';
import styled from 'styled-components';

// 배경화면 import
import LoginBackground from '@assets/images/login.gif';
import MainBackground from '@assets/images/main.gif';
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
import Auth from '@pages/Auth';

// Test Page import
import HoseaKim from '@pages/test/HoseaKim';
import Jignonne from '@pages/test/Jignonne';
import MooMoo from '@pages/test/MooMoo';

const Background = styled.div`
	position: absolute;
	background: linear-gradient(#1b95ec 23%, #2c96d0 47%, #90d4b9 100%);
	background-size: cover;
	background-position: center center;
	background-repeat: no-repeat;

	width: 100vw;
	height: 100vh;
	z-index: -1;
`;

const BackgroundImage = styled.img`
	position: absolute;
	bottom: 0;
	left: 50%;
	transform: translateX(-50%);
	width: 380px;
	object-fit: cover;
	z-index: -1;
`;

const AppRouter = () => {
	const [backgroundImage, setBackgroundImage] = useState<string | undefined>(
		undefined,
	);

	const location = useLocation();
	const loggedIn = !!localStorage.getItem('accessToken ');

	const RequireAuth = ({ children }: { children: JSX.Element }) => {
		const loggedIn = !!localStorage.getItem('accessToken');
		return loggedIn ? children : <Navigate replace to="/login" />;
	};

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
			<Background />
			{backgroundImage && (
				<>
					<BackgroundImage src={backgroundImage} alt="backgroundImage" />
				</>
			)}
			<Routes>
				{/* 메인 라우트 */}
				<Route
					path="/"
					element={loggedIn ? <MainPage /> : <Navigate replace to="/login" />}
				/>
				<Route
					path="/login"
					element={!loggedIn ? <LoginPage /> : <Navigate replace to="/" />}
				/>
				<Route
					path="/room-create"
					element={
						<RequireAuth>
							<RoomCreatePage />
						</RequireAuth>
					}
				/>
				<Route
					path="/room-list"
					element={
						<RequireAuth>
							<RoomListPage />
						</RequireAuth>
					}
				/>
				<Route
					path="/game-waiting"
					element={
						<RequireAuth>
							<GameWaitingPage />
						</RequireAuth>
					}
				/>
				<Route
					path="/game"
					element={
						<RequireAuth>
							<GamePage />
						</RequireAuth>
					}
				/>
				<Route
					path="/character-select"
					element={
						<RequireAuth>
							<CharacterSelect />
						</RequireAuth>
					}
				/>
				<Route
					path="/mypage"
					element={
						<RequireAuth>
							<MyPage />
						</RequireAuth>
					}
				/>
				<Route path="/landing" element={<LandingPage />} />
				<Route
					path="/credit"
					element={
						<RequireAuth>
							<CreditPage />
						</RequireAuth>
					}
				/>
				{/* Social Login */}
				<Route path="/callback" element={<Auth />} />
				{/* 테스트 페이지 라우트  */}
				<Route path="/hoseakim" element={<HoseaKim />} />
				<Route path="/jignonne" element={<Jignonne />} />
				<Route path="/moomoo" element={<MooMoo />} />
			</Routes>
		</>
	);
};

export default AppRouter;
