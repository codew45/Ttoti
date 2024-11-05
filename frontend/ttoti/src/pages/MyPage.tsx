import ProfileBox from "@components/mypage/ProfileBox";
import RoomInfo from "@components/mypage/RoomInfo";
import HomeIcon from "@assets/icons/home.svg?react"
import styled from "styled-components";

const MyPageHeader = styled.div`
	margin-top: 10px;
	padding: 30px;
`;

const ProfileBoxWrapper = styled.div`
  margin-left: 82px; 
`;


const MyPage = () => {
	return <>
      <MyPageHeader>
        <HomeIcon />
        <ProfileBoxWrapper>
          <ProfileBox />
        </ProfileBoxWrapper>
      </MyPageHeader>
			<RoomInfo />
	</>;
};

export default MyPage;
