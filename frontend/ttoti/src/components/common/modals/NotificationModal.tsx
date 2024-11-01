import styled from 'styled-components';
import { Modal, ModalTitle } from '@components/common/modals/ModalCard';
import ListBox from '@components/common/box/ListBox';
import CloseIcon from '@assets/icons/close.svg?react';

const NotificationTop = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: flex-start;
	width: 100%;
`;

const ListColumn = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 5px;
	width: 100%;
`;
const ListRow = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	align-items: flex-end;
	width: 100%;
`;
const ListText = styled.div`
	font-family: 'LINESeed';
	font-weight: normal;
	font-size: 14px;
	width: 100%;
	line-height: 150%;
	padding-top: 3px;
	margin-left: 5px;
`;
const NotificationDivider = styled.div`
	margin-top: 8px;
	width: 260px;
	height: 1px;
	// 해당 컴포넌트에서만 사용되는 색상
	background-color: #928f8f;
`;

const Delete = styled.button`
	font-family: 'LINESeed';
	font-weight: normal;
	font-size: 14px;
	color: ${({ theme }) => theme.colors['danger']};
	background-color: transparent;
	border: 0;
	cursor: pointer;
`;

interface NotificationProps {
	title: string;
	content: string;
}

const NotificationList = ({ title, content }: NotificationProps) => {
	// key-value + map 구현 예정
	return (
		<ListColumn>
			<ListRow>
				<ListBox size="small" ListText={title} />
				<Delete>삭제하기</Delete>
			</ListRow>
			<ListText>{content}</ListText>
			<NotificationDivider />
		</ListColumn>
	);
};

const NotificationModal = () => {
	const subtitleText = '또띠 알림함';
	const titleText = '읽지 않은 알림을 확인해주세요!';

	return (
		<Modal>
			<NotificationTop>
				<ModalTitle titleText={titleText} subtitleText={subtitleText} />
				<CloseIcon />
			</NotificationTop>
			<NotificationList
				title="게임 시작"
				content="또띠 게임이 시작되었습니다."
			/>
			<NotificationList
				title="채팅 알림"
				content="마니또에게 메시지가 왔습니다."
			/>
			<NotificationList
				title="채팅 알림"
				content="마니띠에게 메시지가 왔습니다."
			/>
			<NotificationList title="게임 종료" content="게임 종료 하루 전입니다." />
		</Modal>
	);
};

export default NotificationModal;
