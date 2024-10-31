import styled from "styled-components";
import RoomTitle from "@components/GamePage/RoomTitle/RoomTitle";
import Game from "@components/GamePage/Game";
import BigCloud from "@assets/gamecloud/big-cloud.png";
import SmallCloud from "@assets/gamecloud/small-cloud.png";

// Styled component for the GamePage container
const GamePageContainer = styled.div`
  background-color: #1b95ec;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
`;

// Styled component for clouds
const CloudImage = styled.img`
  position: absolute;
  top: 80px;
  left: 17px;
  width: 72px;
  height: auto;

  &:nth-child(2) {
    top: 113px;
    left: 113px;
  }
`;

const RoomTitleWrapper = styled.div`
  position: absolute;
  display: flex; /* Flexbox 사용 */
  justify-content: flex-end; /* 오른쪽 정렬 */
  width: 250px; /* 원하는 너비 설정 */
  height: 75px;
  top: 62px;
  right: -12px;
`;

const GameWrapper = styled.div`
	position: absolute;
	width: 100%;
	height: 600px;
	bottom: -55px;
`;


const GamePage = () => {
  return (
    <GamePageContainer>
      <CloudImage src={BigCloud} alt="big cloud" />
      <CloudImage src={SmallCloud} alt="small cloud" />
			<RoomTitleWrapper>
    	  <RoomTitle />
			</RoomTitleWrapper>
			<GameWrapper>
      	<Game />
			</GameWrapper>
    </GamePageContainer>
  );
  return (
    <GamePageContainer>
      <CloudImage src={BigCloud} alt="big cloud" />
      <CloudImage src={SmallCloud} alt="small cloud" />
			<RoomTitleWrapper>
    	  <RoomTitle />
			</RoomTitleWrapper>
			<GameWrapper>
      	<Game />
			</GameWrapper>
    </GamePageContainer>
  );
};

export default GamePage;
