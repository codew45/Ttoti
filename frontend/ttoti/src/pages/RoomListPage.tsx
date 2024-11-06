import { useState } from 'react';
import styled from 'styled-components';

import { RoomData } from 'src/types/RoomData';
import RoomCarousel from '@components/room-list/RoomCarousel';
import NotificationModal from '@components/common/modals/NotificationModal';
import EnterCodeModal from '@components/common/modals/EnterCodeModal';

import RowLogo from '@assets/icons/logo/row_logo.svg?react';

const RoomCreateWrapper = styled.div`
	position: relative;
	width: 100vw;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
`;

const ModalBackground = styled.div`
	position: fixed;
	width: 100vw;
	height: 100vh;
	background-color: rgba(0, 0, 0, 0.5);
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 1;
`;

const LogoDiv = styled.div`
	display: flex;
	justify-content: center;
	align-items: center;
	width: 100vw;
	position: absolute;
	top: 70px;
`;
// 임시 데이터로 테스트
const exampleData: RoomData[] = [
	{
		roomId: 1,
		isRoomInProgress: false,
		finishedAt: '2024/11/19 20:00',
		isMemberReady: false,
		memberProfileImageUrl:
			'https://lab.ssafy.com/uploads/-/system/user/avatar/17580/avatar.png?width=800',
		hostName: '정진영',
		roomName: '쭌돌맨사랑해',
		currentParticipants: 8,
		hasUnreadNotifications: true,
	},
	{
		roomId: 2,
		isRoomInProgress: true,
		finishedAt: null,
		isMemberReady: false,
		memberProfileImageUrl:
			'https://lab.ssafy.com/uploads/-/system/user/avatar/17581/avatar.png?width=800',
		hostName: '김호진',
		roomName: '또띠또띠또',
		currentParticipants: 4,
		hasUnreadNotifications: false,
	},
];
const RoomListPage = () => {
	// Modal status 초기화
	const [isModalOpen, setIsModalOpen] = useState(false);
	const [enterModalOpen, setEnterModalOpen] = useState(false);

	const openModal = () => {
		setIsModalOpen(true);
	};

	const closeModal = () => {
		setIsModalOpen(false);
	};

	const openEnterModal = () => {
		setEnterModalOpen(true);
	};

	const closeEnterModal = () => {
		setEnterModalOpen(false);
	};
	return (
		<RoomCreateWrapper>
			<LogoDiv>
				<RowLogo />
			</LogoDiv>
			{isModalOpen && (
				<ModalBackground onClick={closeModal}>
					<NotificationModal onClose={closeModal} />
				</ModalBackground>
			)}
			{enterModalOpen && (
				<ModalBackground>
					{/* onClose 아직 전달 안함 */}
					<EnterCodeModal onClose={closeEnterModal} />
				</ModalBackground>
			)}
			<RoomCarousel
				rooms={exampleData}
				handleModal={openModal}
				handleEnter={openEnterModal}
			/>
		</RoomCreateWrapper>
	);
};

export default RoomListPage;
