import { useEffect, useState } from 'react';
import styled from 'styled-components';

import { RoomData } from 'src/types/RoomData';
import RoomCarousel from '@components/room-list/RoomCarousel';
import NotificationModal from '@components/common/modals/NotificationModal';
import EnterCodeModal from '@components/common/modals/EnterCodeModal';

import RowLogo from '@assets/icons/logo/row_logo.svg?react';
import { getApiClient } from '@services/apiClient';

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

const RoomListPage = () => {
	// Modal status 초기화
	const [rooms, setRooms] = useState<RoomData[]>([]);

	const [isModalOpen, setIsModalOpen] = useState(false);
	const [enterModalOpen, setEnterModalOpen] = useState(false);

	useEffect(() => {
		const fetchRoomData = async () => {
			const apiClient = getApiClient();
			try {
				const res = await apiClient.get('/rooms/my');
				if (res.status === 200) {
					console.log(res.data.body);
					setRooms(res.data.body);
				} else {
					console.log('get failed');
				}
			} catch (error) {
				console.error('API 요청 오류:', error);
			}
		};
		fetchRoomData();
	}, []);

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
					<EnterCodeModal onClose={closeEnterModal} />
				</ModalBackground>
			)}
			<RoomCarousel
				rooms={rooms}
				handleModal={openModal}
				handleEnter={openEnterModal}
			/>
		</RoomCreateWrapper>
	);
};

export default RoomListPage;
