import styled from 'styled-components';
import { RoomCardProps } from 'src/types/RoomData';
import ActiveAlarmIcon from '@assets/icons/active_alarm.svg?react';
import InactiveAlarmIcon from '@assets/icons/inactive_alarm.svg?react';

import RoomInfo from '@components/room-list/RoomInfo';
import GameButtons from '@components/common/buttons/GameButtons';

const RoomInfoWrapper = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	padding: 10px;
	gap: 15px;
	width: 260px;
	height: 255px;
	border-radius: 12px;
	background-color: ${({ theme }) => theme.colors['modal']};
`;

const NotificationBox = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: flex-end;
	align-items: center;
	padding: 0px;
	gap: 10px;
	width: 220px;
	height: 30px;
`;

const DateBox = styled.div<{ color: string }>`
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	width: 185px;
	padding: 10px 18px 6px 16px;
	border-radius: 12px;
	background-color: ${({ theme, color }) => theme.colors[color]};
	font-family: 'LINESeed';
	font-size: 15px;
	font-weight: bold;
	color: ${({ color }) => (color === 'main' ? 'white' : 'black')};
`;

const GoButton = styled(GameButtons)`
	width: 80px;
	height: 32px;
	margin-top: 10px;
	padding-top: 8px;
`;

// 알림 상태 확인 후 아이콘 변경
interface NotificationProps {
	status: boolean;
}
const Notification = ({ status }: NotificationProps) => {
	const alarmIcon =
		status === true ? <ActiveAlarmIcon /> : <InactiveAlarmIcon />;
	return <NotificationBox>{alarmIcon}</NotificationBox>;
};

const RoomCard = ({ room }: RoomCardProps) => {
	console.log(room);
	return (
		<RoomInfoWrapper>
			<Notification status={room.hasUnreadNotifications} />
			{room.finishedAt ? (
				<DateBox color="main">{room.finishedAt}</DateBox>
			) : (
				<DateBox color="background">게임 준비중...</DateBox>
			)}
			<RoomInfo
				hostName={room.hostName}
				roomName={room.roomName}
				currentParticipants={room.currentParticipants}
				imageURL={room.memberProfileImageUrl}
			/>
			<GoButton color="success">입장</GoButton>
		</RoomInfoWrapper>
	);
};

export default RoomCard;
