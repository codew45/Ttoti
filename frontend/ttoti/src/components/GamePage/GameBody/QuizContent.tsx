// QuizContent.tsx
import React from 'react';
import styled from 'styled-components';
import ToggleIcon from '@assets/icons/toggle.svg?react';
import CarouselContainer from './CarouselContainer'; // 새로 분리한 CarouselContainer import

const BodyWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
`;

const SelectContainer = styled.div`
  width: 340px;
  height: 155px;
  padding: 0 10px;
  display: flex;
  align-items: center;
  justify-content: space-between;
`;

const QuizContainer = styled.div`
  width: 280px;
  height: 320px;
  background-color: gray;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  color: white;
`;

const IconButton = styled.button<{ $isLeft?: boolean }>`
  background: none;
  border: none;
  cursor: pointer;
  transform: ${(props) => (props.$isLeft ? 'rotate(180deg)' : 'none')};
`;

interface QuizContentProps {
  page: number;
  togglePage: (direction: 'next' | 'prev') => void;
}

const QuizContent: React.FC<QuizContentProps> = ({ page, togglePage }) => {
  return (
    <BodyWrapper>
      <SelectContainer>
        <IconButton $isLeft onClick={() => togglePage('prev')}>
          <ToggleIcon />
        </IconButton>
        <CarouselContainer page={page} /> {/* 분리한 CarouselContainer 사용 */}
        <IconButton onClick={() => togglePage('next')}>
          <ToggleIcon />
        </IconButton>
      </SelectContainer>
      <QuizContainer>
        {page === 0 ? '마니또' : '마니띠'}
      </QuizContainer>
    </BodyWrapper>
  );
};

export default QuizContent;
