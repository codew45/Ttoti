import styled from 'styled-components';

import {
	RoomInputContainer,
	RoomInputColumn,
	RoomInputExplain,
	RoomInputTitle,
} from '@components/room-create/action/RoomInputCard';

import GameButtons from '@components/common/buttons/GameButtons';

const RoomData = styled(RoomInputTitle)`
	width: 65px;
	font-weight: 400;
`;

const RoomInputData = styled(RoomInputExplain)`
	font-size: 16px;
	font-weight: bold;
`;

const RoomRowContainer = styled.div`
	display: flex;
	flex-direction: row;
	align-items: flex-start;
	padding: 0px;
	gap: 20px;
	margin-bottom: 8px;
`;

const ButtonContainer = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	width: 215px;
`;

interface RoomTotalProps {
	formData: {
		roomName: string;
		RoomParticipants: number;
		RoomPeriod: number;
		RoomTime: string;
	};
}
const RoomTotal = ({ formData }: RoomTotalProps) => {
	// 최종 입력 데이터 확인용 콘솔
	console.log('Room-Input TotalData: ', formData);
	const totalData = [
		{ title: '방 이름', value: formData.roomName },
		{ title: '참여 인원', value: formData.RoomParticipants },
		{ title: '진행 기간', value: formData.RoomPeriod },
		{ title: '종료 시간', value: formData.RoomTime },
	];

	return (
		<RoomInputContainer>
			<RoomInputColumn>
				{totalData.map((data, index) => (
					<RoomRowContainer key={index}>
						<RoomData>{data.title}</RoomData>
						<RoomInputData>{data.value}</RoomInputData>
					</RoomRowContainer>
				))}
			</RoomInputColumn>
			<ButtonContainer>
				<GameButtons color="main">확인</GameButtons>
			</ButtonContainer>
		</RoomInputContainer>
	);
};

export default RoomTotal;
