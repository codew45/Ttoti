import { useEffect } from "react";
import styled from "styled-components";

import { Quiz } from "src/types/QuizTypes";

import choiceAnswer from "@services/apiSelectChoice";

const QuizWrapper = styled.div`
  width: 240px;
  display: flex;
  flex-direction: column;
  gap: 5px;
`;

const MyChoice = styled.div`
font-weight: bold;
font-size: 24px;
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

const TwoChoiceQuizBody = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  gap: 10px;
  `;

const TwoChoiceButton = styled(FourChoiceButton)`
  width: 90px;
  height: 90px;
  border: ${({ $isMatching }) => ($isMatching ? '2px solid red' : '1px solid black')};
`;

const ResultMessage = styled.div`
  position: absolute;
  font-weight: bold;
  font-size: 20px;
  bottom: 15px;
`;

const TwoChoiceQuiz: React.FC<{ $page: number; quiz: Quiz; $isTodayQuiz: boolean; selectedAnswer: string | null; onSelectAnswer: (answer: string) => void }> = ({ $page, quiz, $isTodayQuiz, selectedAnswer, onSelectAnswer }) => {
  const { manittoAnswer, manitiAnswer } = quiz;
  const $isMatching = manittoAnswer === manitiAnswer;
  console.log(quiz);

  let resultMessage = "";
  if (manittoAnswer && manitiAnswer) {
    resultMessage = quiz.quizAnswerIsCorrect ? "서로 같은 답을 골랐네요 !" : "서로 다른 답을 골랐네요 !";
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
    }

    chooseAnswer();

  }, [quiz.ttotiId, quiz.quizId, selectedAnswer])

  return (
    <QuizWrapper>
      {$isTodayQuiz && <MyChoice>나의 선택은 ?</MyChoice>}
      <p>{quiz.quizChoiceContent}</p>
      <TwoChoiceQuizBody>
        {Object.keys(quiz.quizChoiceMap).map((key) => (
          <TwoChoiceButton
            key={key}
            $isTodayQuiz={$isTodayQuiz}
            $isMatching={$isMatching && key === manittoAnswer?.toString()}
            $isManitoAnswer={$page === 0 ? key === manittoAnswer?.toString() : key === manitiAnswer?.toString()}
            $isManitiAnswer={$page === 0 ? key === manitiAnswer?.toString() : key === manittoAnswer?.toString()}
            $isSelected={selectedAnswer === key} // 선택된 상태 전달
            onClick={() => onSelectAnswer(key)} // 클릭 시 선택 상태 업데이트
          >
            {quiz.quizChoiceMap[key]}
          </TwoChoiceButton>
        ))}
      </TwoChoiceQuizBody>
      {!$isTodayQuiz && <ResultMessage>{resultMessage}</ResultMessage>}
    </QuizWrapper>
  );
};

export default TwoChoiceQuiz;