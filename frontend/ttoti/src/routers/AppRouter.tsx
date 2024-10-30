import { Route, Routes } from 'react-router-dom';

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

const AppRouter = () => {
	return (
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
	);
};

export default AppRouter;
