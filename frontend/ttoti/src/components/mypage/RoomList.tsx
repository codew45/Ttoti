import styled from "styled-components";

const RoomListWrapper = styled.div`
  margin-top: 5px;
  display: grid;
  gap: 5px;
  height: 325px;
  overflow-y: auto; /* 수직 스크롤 활성화 */
`;

const Room = styled.div`
  width: 330px;
  height: 140px;
  border: 2px solid black;
  border-radius: 15px;
  background-color: white;
`;

const RoomList = () => {
  return <RoomListWrapper>
    <Room />
    <Room />
    <Room />
    <Room />
    <Room />
  </RoomListWrapper>;
}

export default RoomList