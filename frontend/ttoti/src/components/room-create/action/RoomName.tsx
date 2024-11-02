import styled from 'styled-components';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';

import {
	RoomInputContainer,
	RoomInputColumn,
	RoomInputExplain,
	RoomInputTitle,
} from '@components/room-create/action/RoomInputCard';

const StyledTextField = styled(TextField)`
	.MuiOutlinedInput-root {
		background-color: ${({ theme }) => theme.colors['background']};
		border: 0px solid transparent;
		border-radius: 8px;
		padding-top: 5px;
		font-family: 'LINESeed';
		font-size: 14px;
		width: 215px;
		height: 45px;

		&:hover fieldset {
			border-color: transparent;
		}
	}
`;

const NameInputBox = () => {
	return <StyledTextField placeholder="입력 대기 중..." />;
};

const RoomName = () => {
	return (
		<RoomInputContainer>
			<RoomInputColumn>
				<RoomInputTitle>또띠 방 이름</RoomInputTitle>
				<RoomInputExplain>
					진행할 또띠 방의 이름을 작성해 주세요.
				</RoomInputExplain>
			</RoomInputColumn>
			<NameInputBox />
		</RoomInputContainer>
	);
};

export default RoomName;
