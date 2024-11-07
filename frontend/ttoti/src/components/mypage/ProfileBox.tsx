import ProfileContainer from '@components/common/ProfileComponents';
import profile1 from '@assets/profiles/profile1.png';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { selectMemberName } from '../../stores/slices/userSlice';
import EditNameIcon from '@assets/icons/edit_name.svg?react';

const ProfileWrapper = styled.div`
	width: 130px;
	height: 80px;
	display: flex;
	flex-direction: column;
	align-items: center;
`;

const UserBox = styled.div`
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
`;

const UserName = styled.div`
	margin-top: 5px;
	margin-left: 20px;
	font-family: 'LINESeed';
	font-weight: bold;
	font-size: 15px;
	color: white;
`;

const ProfileBox = () => {
	const userName = useSelector(selectMemberName); // Redux에서 user 이름 가져오기

	return (
		<ProfileWrapper>
			<ProfileContainer src={profile1} size="75px" ready={false} />
			<UserBox>
				<UserName>{userName}</UserName>
				<EditNameIcon style={{ marginLeft: 3, marginTop: 3 }} />
				{/* 나중에 이름 변경 로직 넣어야함. */}
			</UserBox>
		</ProfileWrapper>
	);
};

export default ProfileBox;
