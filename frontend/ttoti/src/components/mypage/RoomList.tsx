import styled from "styled-components";

interface RoomListProps {
  dateRange: [Date, Date]; // 정확히 두 개의 Date를 가지는 배열로 설정
}

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

const RoomList: React.FC<RoomListProps> = () => {

  return <RoomListWrapper>
    <Room />
    <Room />
    <Room />
    <Room />
    <Room />
  </RoomListWrapper>;
}

export default RoomList