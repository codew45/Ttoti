// CarouselContainer.tsx
import React from 'react';
import styled from 'styled-components';

interface CarouselContainerProps {
  page: number;
}

const Container = styled.div<{ $page: number }>`
  width: 280px;
  height: 160px;
  background-color: ${({ $page }) => ($page === 1 ? '#90D4B9' : '#e37c7d')};
  border-radius: 12px;
  border: 1px solid black;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  font-family: "LineSeed";
  color: white;
  transition: transform 0.3s ease;
`;

const TitleText = styled.div`
  font-weight: 300;
  font-size: 16px;
`;

const NameText = styled.div`
  font-weight: normal;
  font-size: 20px;
`;

const CarouselContainer: React.FC<CarouselContainerProps> = ({ page }) => {
  const isManito = page === 0;
  const title = isManito ? '나의 마니또' : '나의 마니띠';
  const name = isManito ? '까칠한 토끼' : '정진영';
  
  return (
    <Container $page={page}>
      <TitleText>{title}</TitleText>
      <NameText>{name}</NameText>
    </Container>
  );
};

export default CarouselContainer;
