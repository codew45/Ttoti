import RoomFilter from "./RoomFilter";
import RoomList from "./RoomList";
import styled from "styled-components";

const RoomWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: -10px;
`;

const RoomBody = () => {
  return <RoomWrapper>
    <RoomFilter />
    <RoomList />
  </RoomWrapper>;
}

export default RoomBody