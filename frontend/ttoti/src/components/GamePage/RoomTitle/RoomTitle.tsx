import styled from "styled-components";

const RoomBox = styled.div`
  width: fit-content; /* 내용에 맞게 자동 조정 */
  height: 75px;
  padding-left: 15px;
  padding-right: 15px; /* 오른쪽 패딩 추가 */
  background-color: white;
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  align-items: flex-start; /* start를 flex-start로 수정 */
  justify-content: center;
`;

const FirstLine = styled.p`
  font-family: 'GmarketSans', sans-serif;
  font-weight: 300; /* Light font weight */
  font-size: 14px;
  margin: 0;
`;

const SecondLine = styled.p`
  font-family: 'GmarketSans', sans-serif;
  font-weight: normal; /* Regular font weight */
  font-size: 24px;
  margin: 0;
`;

const RoomTitle = () => {
  return (
    <RoomBox>
      <FirstLine>정진영의</FirstLine>
      <SecondLine>99NULL뛰기</SecondLine>
    </RoomBox>
  );
};

export default RoomTitle;
