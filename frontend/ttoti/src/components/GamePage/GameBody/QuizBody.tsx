import styled from "styled-components";
import { QuizData, Quiz } from "src/types/QuizTypes"; // Quiz 타입을 가져옵니다.
import React, { useState } from 'react';

interface CarouselContainerProps {
  page: number;
  quizData: QuizData | null;
}

const Container = styled.div<{ $page: number }>`
  width: 280px;
  height: 320px;
  border-radius: 12px;
  border: 1px solid black;
  font-family: "LineSeed";
  background-color: white;
  color: black;
  z-index: 10;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

// 각 QuizType에 따른 컴포넌트
const TwoChoiceQuiz: React.FC<{ quiz: Quiz }> = ({ quiz }) => (
  <div>
    <p>{quiz.quizChoiceContent}</p>
    <div>
      <button>{quiz.quizChoiceMap["1"]}</button>
      <button>{quiz.quizChoiceMap["2"]}</button>
    </div>
  </div>
);

const FourChoiceQuiz: React.FC<{ quiz: Quiz }> = ({ quiz }) => (
  <div>
    <p>{quiz.quizChoiceContent}</p>
    <div>
      <button>{quiz.quizChoiceMap["1"]}</button>
      <button>{quiz.quizChoiceMap["2"]}</button>
      <button>{quiz.quizChoiceMap["3"]}</button>
      <button>{quiz.quizChoiceMap["4"]}</button>
    </div>
  </div>
);

const OXQuiz: React.FC<{ quiz: Quiz }> = ({ quiz }) => (
  <div>
    <p>{quiz.quizChoiceContent}</p>
    <div>
      <button>O</button>
      <button>X</button>
    </div>
  </div>
);

// QuizBody 컴포넌트
const QuizBody: React.FC<CarouselContainerProps> = ({ page, quizData }) => {
  const [currentQuizIndex, setCurrentQuizIndex] = useState(0);

  const isManito = page === 0;
  const todayQuiz = isManito ? quizData?.todayManittoQuiz : quizData?.todayManitiQuiz;
  const quizList = isManito ? quizData?.manittoQuizList : quizData?.manitiQuizList;

  // 오늘의 퀴즈와 퀴즈 리스트를 하나의 리스트로 합침
  const combinedQuizzes = todayQuiz ? [todayQuiz, ...(quizList || [])] : quizList || [];

  // 현재 퀴즈 선택
  const quiz = combinedQuizzes[currentQuizIndex];

  // QuizType에 따른 컴포넌트 선택
  const renderQuiz = (quiz: Quiz) => {
    switch (quiz.quizType) {
      case "TWO_CHOICE":
        return <TwoChoiceQuiz quiz={quiz} />;
      case "FOUR_CHOICE":
        return <FourChoiceQuiz quiz={quiz} />;
      case "OX":
        return <OXQuiz quiz={quiz} />;
      default:
        return <p>Unknown quiz type</p>;
    }
  };

  // 버튼 클릭 이벤트 핸들러
  const handlePrev = () => {
    if (combinedQuizzes.length > 0 && currentQuizIndex > 0) {
      setCurrentQuizIndex(currentQuizIndex - 1);
    }
  };

  const handleNext = () => {
    if (combinedQuizzes.length > 0 && currentQuizIndex < combinedQuizzes.length - 1) {
      setCurrentQuizIndex(currentQuizIndex + 1);
    }
  };

  return (
    <Container $page={page}>
      {quiz ? (
        <>
          {/* 오늘의 퀴즈인지 확인 후 문구 설정 */}
          <p>{quiz === todayQuiz ? "오늘의 퀴즈" : `${quiz?.quizDate}의 퀴즈`}</p>
          {renderQuiz(quiz)}
          {combinedQuizzes.length > 1 && (
            <div>
              <button onClick={handlePrev} disabled={currentQuizIndex === 0}>이전</button>
              <button onClick={handleNext} disabled={currentQuizIndex === combinedQuizzes.length - 1}>다음</button>
            </div>
          )}
        </>
      ) : (
        <p>퀴즈를 불러오는 중...</p>
      )}
    </Container>
  );
};

export default QuizBody;
