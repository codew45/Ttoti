// HoseaKim.tsx
import React, { useState, useEffect, useRef } from 'react';
import styled from 'styled-components';
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
import ProfileContainer from '@components/common/ProfileComponents';

const ModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 310px;
  height: 395px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  position: absolute;             // 절대 위치 지정
  left: 50%;                     // 가로 중앙 정렬
  top: 120px;                    // 상단에서 152px 떨어짐
  transform: translateX(-50%);   // 중앙 정렬을 위한 트랜스폼
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

// const FooterContainer = styled.div`
//   display: flex;
// `

const NextButtonInfo = styled.div`
margin-top: 20px;
  font-family: 'LINESeed';
  font-size: 16px;
  font-weight: 300;
`

const NextButton = styled.button`
  margin-top: 10px;
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

const Modal: React.FC<{ participants: { name: string; imgSrc: string; $ready: boolean; }[] }> = ({ participants }) => {
  const containerRef = useRef<HTMLDivElement>(null);
  const [isOverflow, setIsOverflow] = useState(false);

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
    <ModalContainer>
      <HeaderContainer>
        <HeaderContent>
          <ProfileContainer size="70px" src={profile1} ready={false} />
          <HeaderText>
            <HostName>정진영님의</HostName>
            <RoomName>99NULL</RoomName>
          </HeaderText>
        </HeaderContent>
        <RoomInfo>i</RoomInfo>
      </HeaderContainer>
      <SubText>또띠에 참여하였습니다!</SubText>
      <ParticipantToolbar>
        <RefreshIcon src={refreshIcon} alt='refreshIcon' />
        <MemberToolContainer>
          <MemberNumText>8 / 8</MemberNumText>
          <AddMemberIcon src={addMemberIcon} alt='addMemberIcon' />
        </MemberToolContainer>
      </ParticipantToolbar>
      <ParticipantsContainer ref={containerRef} $isOverflow={isOverflow}>
        <ParticipantBlank></ParticipantBlank>
        {participants.map((participant, index) => (
          <Participant key={index}>
            <ProfileContainer size="70px" src={participant.imgSrc} ready={participant.$ready} />
            <ParticipantName>{participant.name}</ParticipantName>
          </Participant>
        ))}
        <ParticipantBlank></ParticipantBlank>
      </ParticipantsContainer>
      <NextButtonInfo>캐릭터 선택으로 이동할까요?</NextButtonInfo>
      <NextButton>다음</NextButton>
    </ModalContainer>
  );
};

const HoseaKim: React.FC = () => {

  const participants = [
    { name: '정진영', imgSrc: profile1, $ready: true },
    { name: '서지민', imgSrc: profile2, $ready: false },
    { name: '김호진', imgSrc: profile3, $ready: false },
    { name: '권재현', imgSrc: profile4, $ready: false },
    { name: '채이슬', imgSrc: profile5, $ready: false },
    { name: '이상무', imgSrc: profile6, $ready: false },
    { name: '아쿠아', imgSrc: profile7, $ready: false },
    { name: '토오사카', imgSrc: profile8, $ready: false },
  ];

  return (
    <Modal participants={participants} />
  );
};

export default HoseaKim;
