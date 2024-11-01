import styled from 'styled-components';
import InviteModal from '@components/common/modals/InviteModal';
import ListBox from '@components/common/box/ListBox';

const TestWrapper = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 12px;
	background-color: ${({ theme }) => theme.colors['main']};
	height: 100%;
`;

const Jignonne = () => {
	const ListText1 = '스몰 버튼';
	const ListText2 = '스몰';
	return (
		<TestWrapper>
			<h1>Modal</h1>
			<ListBox size="large" ListText={ListText1} />
			<ListBox size="small" ListText={ListText2} />
			<InviteModal />
		</TestWrapper>
	);
};

export default Jignonne;
