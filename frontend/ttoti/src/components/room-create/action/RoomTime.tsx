import { useState } from 'react';
import styled from 'styled-components';
import {
	RoomInputContainer,
	RoomInputColumn,
	RoomInputExplain,
	RoomInputTitle,
} from '@components/room-create/action/RoomInputCard';
import TimeAccountIcon from '@assets/icons/time_account.svg?react';

const RowContainer = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	padding: 0px;
	margin-top: 10px;
	gap: 25px;
	width: 215px;
	font-family: 'LINESeed';
	font-size: 20px;
	font-weight: bold;
`;

const ColumnContainer = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	padding: 0px;
	gap: 10px;
	width: 40px;
`;
const Container = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	padding: 10px 10px 5px 10px;
	width: 40px;
	height: 25px;
	background-color: white;
	border: 1px solid black;
	border-radius: 6px;

	font-family: 'LINESeed';
	font-size: 20px;
`;

// 시간 조작 버튼 생성
const ReversedIcon = styled(TimeAccountIcon)`
	transform: rotate(180deg);
`;

const PlusButton = ({
	onClick,
}: {
	onClick: React.MouseEventHandler<SVGSVGElement>;
}) => {
	return <TimeAccountIcon onClick={onClick} />;
};

const MinusButton = ({
	onClick,
}: {
	onClick: React.MouseEventHandler<SVGSVGElement>;
}) => {
	return <ReversedIcon onClick={onClick} />;
};

const RoomTime = () => {
	const [hour, setHour] = useState(18);
	const [minute, setMinute] = useState(30);

	const incrementHour = () => setHour((prev) => (prev + 1) % 24);
	const decrementHour = () => setHour((prev) => (prev === 0 ? 23 : prev - 1));

	const incrementMinute = () => setMinute((prev) => (prev + 30) % 60);
	const decrementMinute = () =>
		setMinute((prev) => (prev === 0 ? 30 : prev - 30));

	return (
		<RoomInputContainer>
			<RoomInputColumn>
				<RoomInputTitle>또띠 종료 시간</RoomInputTitle>
				<RoomInputExplain>또띠 결과를 몇 시에 공개할까요?</RoomInputExplain>
			</RoomInputColumn>
			<RowContainer>
				<ColumnContainer>
					<PlusButton onClick={incrementHour} />
					<Container>{hour}</Container>
					<MinusButton onClick={decrementHour} />
				</ColumnContainer>
				:
				<ColumnContainer>
					<PlusButton onClick={incrementMinute} />
					<Container>{minute}</Container>
					<MinusButton onClick={decrementMinute} />
				</ColumnContainer>
			</RowContainer>
		</RoomInputContainer>
	);
};

export default RoomTime;
