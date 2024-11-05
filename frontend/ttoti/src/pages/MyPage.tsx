import ProfileBox from "@components/mypage/ProfileBox";
import RoomInfo from "@components/mypage/RoomInfo";
import HomeIcon from "@assets/icons/home.svg?react"
import styled from "styled-components";


const MyPageWrapper = styled.div`
	width:100vw;
	height: 100vh;
	display: flex;
	justify-content: center;
	align-items: center;
`;

const MyPageBox = styled.div`
	display: flex;
	width: 360px;
	flex-direction: column;
	justify-content: center;
	align-items: center;
	position: absolute;

`

const HomeIconWrapper = styled.div`
	position: absolute;
	top: 10px;
	left: 30px;
`;

const MyPageHeader = styled.div`
	margin-top: 10px;
	padding: 30px;
`;

const ProfileBoxWrapper = styled.div`
`;


const MyPage = () => {
	return <MyPageWrapper>
		<MyPageBox>
			<HomeIconWrapper>
      	<HomeIcon />
			</HomeIconWrapper>
      <MyPageHeader>
       	<ProfileBoxWrapper>
         	<ProfileBox />
       	</ProfileBoxWrapper>
      </MyPageHeader>
			<RoomInfo />
		</MyPageBox>
	</MyPageWrapper>;
};

export default MyPage;
