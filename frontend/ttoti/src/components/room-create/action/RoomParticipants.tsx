import { useState } from 'react';
import styled from 'styled-components';
import {
	RoomInputContainer,
	RoomInputColumn,
	RoomInputExplain,
	RoomInputTitle,
} from '@components/room-create/action/RoomInputCard';

import PlusIcon from '@assets/icons/plus_icon.svg?react';
import MinusIcon from '@assets/icons/minus_icon.svg?react';

const ContentContainer = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	gap: 20px;
	width: 215px;
`;

const CountContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	width: 40px;
	height: 40px;
	padding: 10px;
	gap: 10px;
	background-color: ${({ theme }) => theme.colors['background']};
	border-radius: 100%;
	font-family: 'LINESeed';
	font-weight: 400;
	font-size: 24px;
`;

const AlertMessage = styled.div`
	width: 215px;
	color: ${({ theme }) => theme.colors['danger']};
	font-family: 'LINESeed';
	font-size: 12px;
	text-align: center;
`;

const PlusButton = ({
	onClick,
}: {
	onClick: React.MouseEventHandler<SVGSVGElement>;
}) => {
	return <PlusIcon onClick={onClick} />;
};

const MinusButton = ({
	onClick,
}: {
	onClick: React.MouseEventHandler<SVGSVGElement>;
}) => {
	return <MinusIcon onClick={onClick} />;
};

const RoomParticipants = () => {
	const [count, setCount] = useState(5);
	const [alertMessage, setAlertMessage] = useState('');

	const handleIncrease = () => {
		if (count < 8) {
			setCount(count + 1);
			setAlertMessage('');
		} else {
			setAlertMessage('최대 또띠 인원은 8명입니다.');
		}
	};

	const handleDecrease = () => {
		if (count > 3) {
			setCount(count - 1);
		} else {
			setAlertMessage('최소 또띠 인원은 3명입니다.');
		}
	};

	return (
		<RoomInputContainer>
			<RoomInputColumn>
				<RoomInputTitle>또띠 방 인원</RoomInputTitle>
				<RoomInputExplain>참여할 또띠들은 몇 명인가요?</RoomInputExplain>
			</RoomInputColumn>
			<ContentContainer>
				<MinusButton onClick={handleDecrease} />
				<CountContainer>{count}</CountContainer>
				<PlusButton onClick={handleIncrease} />
			</ContentContainer>
			{alertMessage && <AlertMessage>{alertMessage}</AlertMessage>}
		</RoomInputContainer>
	);
};

export default RoomParticipants;
