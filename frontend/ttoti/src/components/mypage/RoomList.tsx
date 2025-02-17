import { useEffect, useState } from "react";
import styled from "styled-components";
import { useNavigate } from 'react-router-dom';

import PlayCredits from '@assets/icons/play_credit.svg?react';
import ListBoldBox from '@components/common/box/ListBoldBox';

import { finishedGameList } from '@services/apiMyPage';

interface RoomListProps {
	dateRange: [Date, Date];
	selectMember: string;
}

interface RoomData {
	roomId: number;
	roomName: string;
	membersName: string[];
	startDate: string;
	endDate: string;
}

const RoomListWrapper = styled.div`
	margin-top: 5px;
	display: grid;
	gap: 5px;
	height: 325px;
	overflow-y: auto;
	position: relative;
`;

const Room = styled.div`
	width: 300px;
	height: 140px;
	border: 2px solid black;
	border-radius: 15px;
	background-color: white;
	padding: 15px;
	display: flex;
	flex-direction: column;
	justify-content: space-between;
`;

const RoomName = styled.div`
	font-weight: bold;
	margin-top: 4px;
`;

const MemberList = styled.div`
	margin-top: 4px;
	width: 200px;
`;

const DateRange = styled.div`
	margin-top: 4px;
`;

const Icon = styled(PlayCredits)`
  margin-left: auto;
  width: 40px;
  height: 40px;
  cursor: pointer;
`;

const ContentWrapper = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  font-family: 'LINESeed';
  gap: 10px;
`;

const RoomList: React.FC<RoomListProps> = ({ dateRange, selectMember }) => {
	const [rooms, setRooms] = useState<RoomData[]>([]);

	const formatDate = (date: Date): string => {
		const year = date.getFullYear();
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const day = String(date.getDate()).padStart(2, '0');
		return `${year}/${month}/${day}`;
	};

	useEffect(() => {
		const fetchGameList = async () => {
			const response = await finishedGameList({
				startDate: formatDate(dateRange[0]),
				endDate: formatDate(dateRange[1]),
				friendId: selectMember,
			});
			setRooms(response);
		};

    fetchGameList();
  }, [dateRange, selectMember]);

  const navigate = useNavigate();

  const handleIconClick = (id: number) => {
    navigate(`/credit/${id}`);
  };

  return (
    <RoomListWrapper>
      {rooms.map((room) => (
        <Room key={room.roomId}>
          <ContentWrapper>
            <ListBoldBox size='small' ListText='방 정보'/>
            <RoomName>{room.roomName}</RoomName>
            <Icon onClick={() => handleIconClick(room.roomId)} />
          </ContentWrapper>
          <ContentWrapper>
            <ListBoldBox size='small' ListText='참여자'/>
            <MemberList>
              {room.membersName.join(", ")}
            </MemberList>
          </ContentWrapper>
          <ContentWrapper>
            <ListBoldBox size='small' ListText='진행기간'/>
            <DateRange>
              {room.startDate} ~ {room.endDate}
            </DateRange>
          </ContentWrapper>
        </Room>
      ))}
    </RoomListWrapper>
  );
};

export default RoomList;
