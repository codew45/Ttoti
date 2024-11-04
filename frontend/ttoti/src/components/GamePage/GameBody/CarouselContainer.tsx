// CarouselContainer.tsx
import React from 'react';
import styled from 'styled-components';
import ProfileContainer from '@components/common/ProfileComponents';
import profile1 from '@assets/profiles/profile1.png';
import profile2 from '@assets/profiles/profile2.png';
import profile3 from '@assets/profiles/profile3.png';
import profile4 from '@assets/profiles/profile4.png';
import heartArrowIcon from '@assets/icons/heartArrowIcon.png';

interface CarouselContainerProps {
  page: number;
}

const Container = styled.div<{ $page: number }>`
  width: 280px;
  height: 150px;
  background-color: ${({ $page }) => ($page === 1 ? '#90D4B9' : '#e37c7d')};
  border-radius: 12px;
  border: 1px solid black;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: "LineSeed";
  color: white;
`;

const TitleText = styled.div`
  font-weight: 300;
  font-size: 16px;
`;

const NameText = styled.div`
  font-weight: normal;
  font-size: 20px;
  margin-bottom: 8px;
`;

const ProfileImagesContainer = styled.div`
  display: flex;
  gap: 10px;
`;

const HeartIcon = styled.img`
  width: 64px; // 아이콘 크기 조절
  height: 64px;
`;

const CarouselContainer: React.FC<CarouselContainerProps> = ({ page }) => {
  const isManito = page === 0;
  const title = isManito ? '나의 마니또' : '나의 마니띠';
  const name = isManito ? '까칠한 토끼' : '정진영';

  return (
    <Container $page={page}>
      <TitleText>{title}</TitleText>
      <NameText>{name}</NameText>
      <ProfileImagesContainer>
        {isManito ? (
          <>
            <ProfileContainer src={profile1} size="64px" ready={false}/>
            <HeartIcon src={heartArrowIcon} alt="Heart Arrow Icon" />
            <ProfileContainer src={profile2} size="64px" ready={false}/>
          </>
        ) : (
          <>
            <ProfileContainer src={profile3} size="64px" ready={false}/>
            <HeartIcon src={heartArrowIcon} alt="Heart Arrow Icon" />
            <ProfileContainer src={profile4} size="64px" ready={false}/>
          </>
        )}
      </ProfileImagesContainer>
    </Container>
  );
};

export default CarouselContainer;
