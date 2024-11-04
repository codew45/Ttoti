import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import TextField from '@mui/material/TextField';

import {
	Modal,
	ModalTitle,
	ButtonContainer,
} from '@components/common/modals/ModalCard';
import ListBox from '@components/common/box/ListBox';
import { useState } from 'react';

const EnterColumn = styled.div`
	display: flex;
	flex-direction: column;
	gap: 10px;
`;

const StyledTextField = styled(TextField)`
	.MuiOutlinedInput-root {
		background-color: ${({ theme }) => theme.colors['background']};
		border: 0px solid transparent;
		border-radius: 8px;
		padding-top: 5px;
		font-family: 'LINESeed';
		font-size: 14px;
		width: 290px;
		height: 45px;
		z-index: 1;

		&:hover fieldset {
			border-color: transparent;
		}
	}
`;

interface CodeInputProps {
	inputCode: string;
	onInputChange: (value: string) => void;
}

const CodeInputBox = ({ inputCode, onInputChange }: CodeInputProps) => {
	const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
		const newValue = event.target.value;
		// 8글자 이하일 때만 상태 업데이트
		if (newValue.length <= 8) {
			onInputChange(newValue);
		}
	};
	return (
		<StyledTextField
			placeholder="초대 코드를 입력해 주세요."
			value={inputCode}
			onChange={handleChange}
		/>
	);
};

const EnterContents = () => {
	const [inputCode, setInputCode] = useState('');
	const handleInputChange = (value: string) => {
		setInputCode(value);
	};
	return (
		<EnterColumn>
			<ListBox size="small" ListText="초대 코드" />
			<CodeInputBox inputCode={inputCode} onInputChange={handleInputChange} />
		</EnterColumn>
	);
};

interface EnterModalProps {
	onClose: () => void;
}

const EnterCodeModal = ({ onClose }: EnterModalProps) => {
	const subtitleText = '초대 코드 입력';
	const titleText = '코드를 입력하여 또띠를 시작해보세요!';
	const explainText = '';
	const buttonColor1 = 'success';
	const buttonText1 = '또띠 입장하기';
	const buttonColor2 = 'background';
	const buttonText2 = '닫기';

	const navigate = useNavigate();

	// 코드 유효성 검사 아직 안함, 바로 게임 페이지로 진입으로 작성중
	// API 연결 후 변경 예정
	const handleEnter = () => {
		navigate('/game');
	};

	return (
		<Modal>
			<ModalTitle titleText={titleText} subtitleText={subtitleText} />
			<EnterContents />
			<ButtonContainer
				explainText={explainText}
				buttonColor1={buttonColor1}
				buttonText1={buttonText1}
				onClick1={handleEnter}
				buttonColor2={buttonColor2}
				buttonText2={buttonText2}
				onClick2={onClose}
			/>
		</Modal>
	);
};

export default EnterCodeModal;
