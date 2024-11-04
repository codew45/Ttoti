import styled from 'styled-components';

import CreateCard from '@components/room-create/CreateCard';

const RoomCreateWrapper = styled.div`
	position: relative;
	background-color: rgba(0, 0, 0, 0.5);
	width: 100vw;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
`;

const RoomCreatePage = () => {
	return (
		<RoomCreateWrapper>
			<CreateCard />
		</RoomCreateWrapper>
	);
};

export default RoomCreatePage;
