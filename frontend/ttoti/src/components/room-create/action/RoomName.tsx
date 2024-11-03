import styled from 'styled-components';
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

interface RoomNameProps {
	formData: { roomName: string };
	onInputChange: (name: 'roomName', value: string) => void;
}

const NameInputBox = ({ formData, onInputChange }: RoomNameProps) => {
	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const newValue = event.target.value;
		// 8글자 이하일 때만 상태 업데이트
		if (newValue.length <= 8) {
			onInputChange('roomName', newValue);
		}
	};
	return (
		<StyledTextField
			slotProps={{
				formHelperText: {
					sx: { textAlign: 'right', fontFamily: 'LINESeed', marginTop: '5px' },
				}, // 글자 수 표시 위치 커스텀
			}}
			placeholder="최대 8글자까지 입력 가능합니다."
			value={formData.roomName}
			onChange={handleChange}
			helperText={`${formData.roomName.length}/8`} // 글자 수 표시
		/>
	);
};

const RoomName = ({ formData, onInputChange }: RoomNameProps) => {
	return (
		<RoomInputContainer>
			<RoomInputColumn>
				<RoomInputTitle>또띠 방 이름</RoomInputTitle>
				<RoomInputExplain>
					진행할 또띠 방의 이름을 작성해 주세요.
				</RoomInputExplain>
			</RoomInputColumn>
			<NameInputBox formData={formData} onInputChange={onInputChange} />
		</RoomInputContainer>
	);
};

export default RoomName;
