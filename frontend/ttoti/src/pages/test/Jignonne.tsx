import styled from 'styled-components';
import { MainModal } from '@components/common/modals/MainModal';

const TestWrapper = styled.div`
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 12px;
	background-color: ${({ theme }) => theme.colors['main']};
	height: 100%;
`;

const Jignonne = () => {
	return (
		<TestWrapper>
			<h1>Modal</h1>
			<MainModal />
		</TestWrapper>
	);
};

export default Jignonne;
