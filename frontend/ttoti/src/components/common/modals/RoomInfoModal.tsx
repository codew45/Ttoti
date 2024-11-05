// RoomInfoModal.tsx
import React from 'react';
import styled from 'styled-components';

interface InfoItem {
  label: string;
  value: string;
}

interface RoomInfoModalProps {
  onClose: () => void;
  onLeave: () => void;
  infoList: InfoItem[];
}

const RoomInfoModal: React.FC<RoomInfoModalProps> = ({ onClose, onLeave, infoList }) => {
  return (
    <ModalOverlay onClick={onClose}>
      <ModalContainer onClick={(e) => e.stopPropagation()}>
        <HeaderContainer>
          <Title>방 정보</Title>
          <Description>또띠 방 정보를 확인해보세요!</Description>
        </HeaderContainer>
        
        <InfoList>
          {infoList.map((item, index) => (
            <InfoBox key={index}>
              <InfoLabel>{item.label}</InfoLabel>
              <InfoValue>{item.value}</InfoValue>
            </InfoBox>
          ))}
        </InfoList>
        
        <ButtonContainer>
          <LeaveButton onClick={onLeave}>나가기</LeaveButton>
          <CloseButton onClick={onClose}>닫기</CloseButton>
        </ButtonContainer>
      </ModalContainer>
    </ModalOverlay>
  );
};

export default RoomInfoModal;

const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`;

const ModalContainer = styled.div`
  display: grid;
  background-color: #F6F7FB;
  border-radius: 15px;
  width: 280px;
  padding: 30px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  z-index: 1001;
  gap: 17px;
`;

const HeaderContainer = styled.header`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 10px;
`;

const Title = styled.h2`
  font-family: 'LINESeed';
  font-size: 16px;
  font-weight: normal;
  color: #555;
  margin: 0;
`;

const Description = styled.h3`
  font-family: 'LINESeed';
  font-size: 16px;
  font-weight: bold;
  color: #333;
  margin: 0;
`;

const InfoList = styled.ul`
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 0;
  list-style: none;
`;

const InfoBox = styled.li`
  display: flex;
  flex-direction: row;
  width: 100%;
`;

const InfoLabel = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 17px;
  font-family: 'GmarketSans';
  font-size: 14px;
  font-weight: bold;
  line-height: 16px;
  color: white;
  background-color: #7984fc;
  padding-top: 4px;
  border-radius: 15px;
  margin-right: 15px;
`;

const InfoValue = styled.div`
  width: 185px;
  font-family: 'LINESeed';
  font-size: 16px;
  font-weight: normal;
  color: black;
  padding-top: 4px;
`;

const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 17px;
`;

const LeaveButton = styled.button`
  width: 210px;
  height: 40px;
  background-color: #ff6430;
  font-family: 'LINESeed';
  font-size: 15px;
  font-weight: normal;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
`;

const CloseButton = styled.button`
  width: 210px;
  height: 40px;
  background-color: #E1E9EF;
  font-family: 'LINESeed';
  font-size: 15px;
  font-weight: normal;
  color: black;
  border: none;
  border-radius: 6px;
  cursor: pointer;
`;
