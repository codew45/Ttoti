import styled from 'styled-components';
import { RoomData } from 'src/types/RoomData';
import RoomCard from './RoomCard';
import RoomEnterCard from './RoomEnterCard';

interface RoomCarouselProps {
	rooms: RoomData[];
	handleModal: () => void;
	handleEnter: () => void;
}

const Carousel = styled.div`
	display: flex;
	flex-direction: row;
	align-items: center;
	width: 360px;
	padding: 0px 40px;
	gap: 20px;
	overflow-x: scroll;
`;

const RoomCarousel = ({
	rooms,
	handleModal,
	handleEnter,
}: RoomCarouselProps) => {
	return (
		<Carousel>
			{rooms.map((room) => (
				<div key={room.roomId}>
					<RoomCard room={room} onNotificationClick={handleModal} />
				</div>
			))}
			<div>
				<RoomEnterCard onEnterClick={handleEnter} />
			</div>
		</Carousel>
	);
};

export default RoomCarousel;
