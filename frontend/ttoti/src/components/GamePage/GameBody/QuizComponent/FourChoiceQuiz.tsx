import { useEffect } from "react";
import styled from "styled-components";

import { Quiz } from "src/types/QuizTypes";

import choiceAnswer from "@services/apiSelectChoice";

const FourChoiceQuizBody = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 15px;
`;

const FourChoiceButton = styled.button<{
  $isTodayQuiz: boolean;
  $isMatching: boolean;
  $isManitoAnswer: boolean;
  $isManitiAnswer: boolean;
  $isSelected: boolean;
}>`
  width: 240px;
  height: 30px;
  margin-top: 3px;
  border-radius: 10px;
  border: ${({ $isMatching }) => ($isMatching ? '2px solid red' : 'none')};
  background-color: ${({ $isTodayQuiz, $isSelected, $isMatching, $isManitoAnswer, $isManitiAnswer }) =>
    $isTodayQuiz && $isSelected ? '#67C431' : // 선택된 답변 강조 (노란색) - 오늘의 퀴즈인 경우에만
    $isMatching ? '#67C431' :
    $isManitoAnswer ? '#67C431' :
    $isManitiAnswer ? '#FF6430' :
    '#E1E9EF'};
`;

const MyChoice = styled.div`
font-weight: bold;
font-size: 24px;
`;

const QuizWrapper = styled.div`
  width: 240px;
  display: flex;
  flex-direction: column;
  gap: 5px;
`;

const ResultMessage = styled.div`
  position: absolute;
  font-weight: bold;
  font-size: 20px;
  bottom: 15px;
`;

const FourChoiceQuiz: React.FC<{ 
  $page: number; 
  quiz: Quiz; 
  $isTodayQuiz: boolean; 
  selectedAnswer: string | null; 
  onSelectAnswer: (answer: string) => void; 
}> = ({ $page, quiz, $isTodayQuiz, selectedAnswer, onSelectAnswer }) => {
  const { manittoAnswer, manitiAnswer } = quiz;
  const $isMatching = manittoAnswer === manitiAnswer;

  let resultMessage = "";
  if (manittoAnswer && manitiAnswer) {
    resultMessage = quiz.quizAnswerIsCorrect ? "서로 같은 답을 골랐네요 !" : "틀렸누 ㅋ";
  } else {
    resultMessage = "답을 고르지 않은 또띠가 있어요 !";
  }

  useEffect(() => {
    const chooseAnswer = async () => {
      try {
        if (selectedAnswer) {
          await choiceAnswer(quiz.ttotiId, quiz.quizId, Number(selectedAnswer));
        }
      } catch (error) {
        console.error(error);
      }
    };

    chooseAnswer();
  }, [quiz.ttotiId, quiz.quizId, selectedAnswer]);

  return (
    <QuizWrapper>
      {$isTodayQuiz && <MyChoice>나의 선택은 ?</MyChoice>}
      <span style={{marginTop: 10}}>{quiz.quizChoiceContent}</span>
      <FourChoiceQuizBody>
        {Object.keys(quiz.quizChoiceMap).map((key) => (
          <FourChoiceButton
            key={key}
            $isTodayQuiz={$isTodayQuiz}
            $isMatching={$isMatching && key === manittoAnswer?.toString()}
            $isManitoAnswer={$page === 0 ? key === manittoAnswer?.toString() : key === manitiAnswer?.toString()}
            $isManitiAnswer={$page === 0 ? key === manitiAnswer?.toString() : key === manittoAnswer?.toString()}
            $isSelected={selectedAnswer === key}
            onClick={() => onSelectAnswer(key)}
          >
            {quiz.quizChoiceMap[key]}
          </FourChoiceButton>
        ))}
      </FourChoiceQuizBody>
      {!$isTodayQuiz && <ResultMessage>{resultMessage}</ResultMessage>}
    </QuizWrapper>
  );
};

export default FourChoiceQuiz;
