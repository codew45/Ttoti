import styled from 'styled-components';
import { RoomData } from 'src/types/RoomData';
import RoomCard from './RoomCard';
import RoomInviteCard from './RoomInviteCard';

interface RoomCarouselProps {
	rooms: RoomData[];
}

const Carousel = styled.div`
	display: flex;
	flex-direction: row;
	align-items: center;
	padding: 0px 40px;
	gap: 20px;
	overflow-x: scroll;
`;

const RoomCarousel = ({ rooms }: RoomCarouselProps) => {
	return (
		<Carousel>
			{rooms.map((room) => (
				<div key={room.roomId}>
					<RoomCard room={room} />
				</div>
			))}
			<div>
				<RoomInviteCard />
			</div>
		</Carousel>
	);
};

export default RoomCarousel;
