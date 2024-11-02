import styled from 'styled-components';

import RoomName from '@components/room-create/action/RoomName';
import RoomParitipants from '@components/room-create/action/RoomParitipants';
import RoomPeriod from '@components/room-create/action/RoomPeriod';
import RoomTime from '@components/room-create/action/RoomTime';

import CloseIcon from '@assets/icons/close.svg?react';
import ToggleIcon from '@assets/icons/toggle.svg?react';
import ToggleActiveIcon from '@assets/icons/toggle_active.svg?react';
import { useState } from 'react';

const CreateCardBox = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	width: 270px;
	padding: 29px 20px;
	background-color: ${({ theme }) => theme.colors['modal']};
	border-radius: 12px;
`;

const CloseDiv = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: flex-end;
	width: 260px;
	margin-bottom: 10px;
`;

const CreateContainer = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: center;
	width: 280px;
`;

const RotatedInactive = styled(ToggleIcon)`
	transform: rotate(180deg);
`;
const RotatedActive = styled(ToggleActiveIcon)`
	transform: rotate(180deg);
`;

const LeftButton = ({
	onClick,
	currentIndex,
}: {
	onClick: React.MouseEventHandler<SVGSVGElement>;
	currentIndex: number;
}) => {
	return currentIndex === 0 ? (
		<RotatedInactive />
	) : (
		<RotatedActive onClick={onClick} />
	);
};

const RightButton = ({
	onClick,
}: {
	onClick: React.MouseEventHandler<SVGSVGElement>;
}) => {
	return <ToggleActiveIcon onClick={onClick} />;
};

const ContentWrapper = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	gap: 33px;
	padding: 0px;
	width: 215px;
	min-height: 180px;
`;
const HeadText = styled.div`
	width: 215px;
	font-family: 'GmarketSans';
	font-weight: bold;
	font-size: 24px;
`;

// 방 만들기 컴포넌트
const createComponents = [
	<RoomName />,
	<RoomParitipants />,
	<RoomPeriod />,
	<RoomTime />,
];

const Content = ({ index }: { index: number }) => {
	return (
		<ContentWrapper>
			<HeadText>방 만들기</HeadText>
			{createComponents[index]}
		</ContentWrapper>
	);
};

const CreateCard = () => {
	// 카드 컴포넌트 인덱스 초기화
	const [currentIndex, setCurrentIndex] = useState(0);

	// 페이지 이동 이동 함수 작성
	const handlePrevious = () => {
		setCurrentIndex((prevIndex) => (prevIndex === 0 ? 0 : prevIndex - 1));
	};

	const handleNext = () => {
		setCurrentIndex((prevIndex) =>
			prevIndex === createComponents.length - 1
				? createComponents.length - 1
				: prevIndex + 1,
		);
	};

	return (
		<CreateCardBox>
			<CloseDiv>
				<CloseIcon />
			</CloseDiv>
			<CreateContainer>
				<LeftButton onClick={handlePrevious} currentIndex={currentIndex} />
				<Content index={currentIndex} />
				<RightButton onClick={handleNext} />
			</CreateContainer>
		</CreateCardBox>
	);
};

export default CreateCard;
