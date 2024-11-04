import styled from 'styled-components';
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
	background: rgba(255, 255, 255, 0.5);
`;

const ColumnContainer = styled.div`
	display: flex;
	flex-direction: column;
	justify-content: space-between;
	align-items: center;
	padding: 0px;
	gap: 10px;
	width: 162px;
`;

const ExplainText = styled.div`
	font-family: 'GmarketSans';
	font-weight: 300;
	font-size: 14px;
`;
const GoButton = styled(GameButtons)`
	background-color: ${({ theme }) => theme.colors['info']};
	padding-top: 10px;
	width: 165px;
`;

const RoomInviteCard = () => {
	return (
		<RoomInfoWrapper>
			<ColumnContainer>
				<ExplainText>초대 코드를 입력해주세요!</ExplainText>
				<GoButton color="info">초대 코드로 입장</GoButton>
			</ColumnContainer>
		</RoomInfoWrapper>
	);
};

export default RoomInviteCard;
