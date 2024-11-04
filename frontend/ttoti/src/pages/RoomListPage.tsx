import styled from 'styled-components';

import RoomCarousel from '@components/room-list/RoomCarousel';
import { RoomData } from 'src/types/RoomData';
const RoomCreateWrapper = styled.div`
	position: relative;
	width: 360px;
	height: 800px;
	display: flex;
	justify-content: center;
	align-items: center;
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
	return (
		<RoomCreateWrapper>
			<RoomCarousel rooms={exampleData} />
		</RoomCreateWrapper>
	);
};

export default RoomListPage;
