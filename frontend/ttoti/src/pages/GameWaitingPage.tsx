// GameWaitingPage.tsx
import React, { useState, useEffect, useRef, useMemo, useCallback } from 'react';
import styled from 'styled-components';
import { useNavigate, useParams } from 'react-router-dom';
import getRoomData, { RoomMember, RoomData } from '@services/game-waiting-page/waitingRoomInfo'; // 파일 위치에 맞게 변경
import profile1 from '@assets/profiles/profile1.png';
import profile2 from '@assets/profiles/profile2.png';
import profile3 from '@assets/profiles/profile3.png';
import profile4 from '@assets/profiles/profile4.png';
import profile5 from '@assets/profiles/profile5.png';
import profile6 from '@assets/profiles/profile6.png';
import profile7 from '@assets/profiles/profile7.png';
import profile8 from '@assets/profiles/profile8.png';
import refreshIcon from '@assets/profiles/refreshIcon.png';
import addMemberIcon from '@assets/profiles/addMemberIcon.png';
import monkey from '@assets/characters/Monkey_pixcel.png';
import ProfileContainer from '@components/common/ProfileComponents';
import InviteModal from '@components/common/modals/InviteModal';
import RoomInfoModal from '@components/common/modals/RoomInfoModal';

const GameWaitingPage: React.FC = () => {
  console.log('GameWaitingPage Mounted')
  const { id: roomId } = useParams<{ id: string }>();
  const [roomData, setRoomData] = useState<RoomData | null>(null);
  const containerRef = useRef<HTMLDivElement>(null);
  const [isOverflow, setIsOverflow] = useState(false);
  const [showNextButton, setShowNextButton] = useState(true);
  const [$isInviteModalOpen, setInviteModalOpen] = useState(false);
  const [$isRoomInfoModalOpen, setRoomInfoModalOpen] = useState(false);
  const navigate = useNavigate();

  const fetchRoomData = useCallback(async () => {
    if (!roomId) return;
    try {
      const data = await getRoomData(roomId);
      setRoomData(data);
    } catch (err) {
      console.error(err);
    } finally {
      console.log('fetchRoomData fine');
    }
  }, [roomId]);

  useEffect(() => {
    fetchRoomData();
  }, [fetchRoomData]);

  const participants = useMemo(() => [
    { name: '정진영', imgSrc: profile1, $ready: true },
    { name: '서지민', imgSrc: profile2, $ready: false },
    { name: '김호진', imgSrc: profile3, $ready: false },
    { name: '권재현', imgSrc: profile4, $ready: false },
    { name: '채이슬', imgSrc: profile5, $ready: false },
    { name: '이상무', imgSrc: profile6, $ready: false },
    { name: '아쿠아', imgSrc: profile7, $ready: false },
    { name: '토오사카', imgSrc: profile8, $ready: false },
  ], []);

  const infoList = [
    { label: '방 이름', value: '99NULL' },
    { label: '방장', value: '정진영' },
    { label: '종료 시간', value: '18:30' },
    { label: '진행 기간', value: '7일' },
  ];

  const openModal = () => setRoomInfoModalOpen(true);
  const closeModal = () => setRoomInfoModalOpen(false);
  const openInviteModal = () => setInviteModalOpen(true);
  const closeInviteModal = () => setInviteModalOpen(false);
  const handleNextButtonClick = () => setShowNextButton(false);
  const handleRetryButtonClick = () => navigate('/character-select');

  useEffect(() => {
    const handleResize = () => {
      const container = containerRef.current;
      if (container && container.scrollWidth > container.clientWidth) {
        setIsOverflow(true);
      } else {
        setIsOverflow(false);
      }
    };

    handleResize();
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, [participants]);

  return (
    <>
      <ModalContainer>
        <HeaderContainer>
          <HeaderContent>
            <ProfileContainer size="70px" src={profile1} ready={false} />
            <HeaderText>
              <HostName>{roomData?.hostName ?? "방장 정보 없음"}님의</HostName>
              <RoomName>{roomData?.roomName ?? "방 이름 없음"}</RoomName>
            </HeaderText>
          </HeaderContent>
          <RoomInfo onClick={openModal}>i</RoomInfo>
        </HeaderContainer>
        <SubText>또띠에 참여하였습니다!</SubText>
        <ParticipantToolbar>
          <RefreshIcon src={refreshIcon} alt='refreshIcon' />
          <MemberToolContainer>
            <MemberNumText>8 / 8</MemberNumText>
            <AddMemberIcon onClick={openInviteModal} src={addMemberIcon} alt='addMemberIcon' />
          </MemberToolContainer>
        </ParticipantToolbar>
        <ParticipantsContainer ref={containerRef} $isOverflow={isOverflow}>
          <ParticipantBlank />
          {roomData?.roomMemberInfo.roomMemberList?.map((member: RoomMember) => (
            <Participant key={member.name}>
              <ProfileContainer size="70px" src={member.profileImageUrl} ready={member.isReady} />
              <ParticipantName>{member.name}</ParticipantName>
            </Participant>
          ))}
          <ParticipantBlank />
        </ParticipantsContainer>
        <FooterContainer>
          {showNextButton ? (
            <>
              <NextButtonInfo>캐릭터 선택으로 이동할까요?</NextButtonInfo>
              <NextButton onClick={handleNextButtonClick}>다음</NextButton>
            </>
          ) : (
            <>
              <SelectedContainer>
                <CharacterImage src={monkey} alt="monkey" />
                <SelectedTextBox>
                  <CompleteText>선택 완료!</CompleteText>
                  <ToggleButton onClick={handleRetryButtonClick}>다시 선택</ToggleButton>
                </SelectedTextBox>
              </SelectedContainer>
            </>
          )}
        </FooterContainer>
      </ModalContainer>
      
      {$isInviteModalOpen && (
        <InviteModal onClose={closeInviteModal} />
      )}

      {$isRoomInfoModalOpen && (
        <RoomInfoModal
          onClose={closeModal}
          onLeave={() => {
            closeModal();
            alert("방에서 나갔습니다.");
          }}
          infoList={infoList}
        />
      )}
    </>
  );
};

export default GameWaitingPage;

const ModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 310px;
  height: 395px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -65%);
`;

const HeaderContainer = styled.div`
  display: flex;
  align-items: center;
  margin-top: 30px;
  width: 250px;
  height: 80px;
  background-color: #90D4B9;
  border-radius: 9px;
`;

const HeaderContent = styled.div`
  display: flex;
  flex-direction: row;
  margin-left: 15px;
  margin-right: 15px;
  width: auto;
  height: 70px;
`

const HeaderText = styled.div`
  display: flex;
  flex-direction: column;
  margin: auto;
  margin-left: 15px;
`

const HostName = styled.div`
  font-family: 'GmarketSans';
  font-size: 14px;
  font-weight: 300;
`

const RoomName = styled.div`
  font-family: 'GmarketSans';
  font-size: 24px;
  font-weight: normal;
`

const RoomInfo = styled.div`
  margin-top: 50px;
  margin-left: 3px;
  font-family: 'GmarketSans';
  font-size: 12px;
  font-weight: Bold;
  color: #90D4B9;
  text-align: center;
  line-height: 21px;
  width: 17.5px;
  height: 17.5px;
  background-color: white;
  border-radius: 50%;
  cursor: pointer;
`

const SubText = styled.div`
  margin-top: 10px;
  font-family: 'LINESeed';
  font-size: 16px;
  font-weight: 300;
`;

const ParticipantToolbar = styled.div`
  margin-top: 15px;
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 27px;
`

const RefreshIcon = styled.img`
  margin-left: 11px;
  width: 19px;
  height: 19px;
`

const MemberToolContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 5px;
`

const MemberNumText = styled.div`
  display: flex;
  height: 16px;
  font-family: 'LINESeed';
  font-size: 16px;
  font-weight: 300;
`

const AddMemberIcon = styled.img`
  width: 19px;
  height: 19px;
  margin-right: 10px;
  cursor: pointer;
`

const ParticipantsContainer = styled.div<{ $isOverflow: boolean }>`
  display: flex;
  align-items: center;
  justify-content: ${({ $isOverflow }) => ($isOverflow ? 'flex-start' : 'center')};
  width: 100%;
  height: 100px;
  overflow-x: auto;
  white-space: nowrap;
  gap: 10px;
  background-color: #E1E9EF;
`;

const ParticipantBlank = styled.div`
  width: 12px;
  height: auto;
`

const Participant = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const ParticipantName = styled.div`
  margin-top: 5px;
  font-family: 'SB어그로';
  font-size: 12px;
  font-weight: 300;
`;

const FooterContainer = styled.footer`
  flex: 1 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
  gap: 10px;
`

const NextButtonInfo = styled.div`
  font-family: 'LINESeed';
  font-size: 16px;
  font-weight: 300;
`

const NextButton = styled.button`
  padding: 10px;
  width: 115px;
  height: 35px;
  font-family: 'GmarketSans';
  font-size: 16px;
  font-weight: bold;
  background-color: #1B95EC;
  color: white;
  border: none;
  border-radius: 15px;
  cursor: pointer;
`;

const SelectedContainer = styled.div`
  display: flex;
  flex-direction: row;
  width: 228px;
  height: 74px;
  gap: 31px;
`

const CharacterImage = styled.img`
  padding-left: 8px;
  padding-right: 8px;
`

const SelectedTextBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 110px;
`

const CompleteText = styled.p`
  margin-top: 5px;
  margin-bottom: 5px;
  font-family: 'GmarketSans';
  font-size: 24px;
  font-weight: bold;
  color: #67c431;
`

const ToggleButton = styled.button`
  align-self: center;
  margin-top: 5px;
  padding: 8px;
  width: 90px;
  height: 30px;
  font-family: 'GmarketSans';
  font-size: 16px;
  font-weight: bold;
  background-color: #ff6430;
  color: white;
  border: none;
  border-radius: 15px;
  cursor: pointer;
`;
