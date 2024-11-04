import styled from 'styled-components';
import {
	Modal,
	ModalTitle,
	ButtonContainer,
} from '@components/common/modals/ModalCard';
import ListBox from '@components/common/box/ListBox';
import DuplicateIcon from '@assets/icons/duplicate.svg?react';
import { useNavigate } from 'react-router-dom';

const InviteWrapper = styled.div`
	display: flex;
	flex-direction: column;
	gap: 15px;
	width: 100%;
`;

const InviteColumn = styled.div`
	display: flex;
	flex-direction: column;
	gap: 10px;
`;

const InviteBox = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: center;
	padding: 5px 10px;
	height: 35px;
	border: 1px solid black;
	border-radius: 6px;
`;

const InviteText = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	padding-top: 3px;
	font-family: 'LINESeed';
	font-size: 16px;
	font-weight: 300;
`;

const InviteContents = () => {
	return (
		<InviteWrapper>
			<InviteColumn>
				<ListBox size="small" ListText="초대 링크" />
				<InviteBox>
					<InviteText>https://ttoti.invte</InviteText>
					<DuplicateIcon />
				</InviteBox>
			</InviteColumn>
			<InviteColumn>
				<ListBox size="small" ListText="초대 코드" />
				<InviteBox>
					<InviteText>$an@mu97</InviteText>
					<DuplicateIcon />
				</InviteBox>
			</InviteColumn>
		</InviteWrapper>
	);
};

const InviteModal = () => {
	const subtitleText = '또띠 초대';
	const titleText = '친구를 또띠에 초대해주세요!';
	const explainText = '';
	const buttonColor1 = 'login';
	const buttonText1 = '카카오톡 공유하기';
	const buttonColor2 = 'background';
	const buttonText2 = '닫기';

	const navigate = useNavigate();

	const handleInvite = () => {
		navigate('/game');
	};

	const handleClose = () => {
		console.log('모달 close를 연결해 주세요.');
	};

	return (
		<Modal>
			<ModalTitle titleText={titleText} subtitleText={subtitleText} />
			<InviteContents />
			<ButtonContainer
				explainText={explainText}
				buttonColor1={buttonColor1}
				buttonText1={buttonText1}
				onClick1={handleInvite}
				buttonColor2={buttonColor2}
				buttonText2={buttonText2}
				onClick2={handleClose}
			/>
		</Modal>
	);
};

export default InviteModal;
