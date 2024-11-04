import styled from "styled-components";
import { QuizData, Quiz } from "src/types/QuizTypes"; // Quiz 타입을 가져옵니다.

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
  const isManito = page === 0;
  const quiz = isManito ? quizData?.todayManittoQuiz : quizData?.todayManitiQuiz;

  // QuizType에 따른 컴포넌트 선택
  const renderQuiz = (quiz: Quiz) => { // 여기서 Quiz 타입을 사용합니다.
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

  return (
    <Container $page={page}>
      {quiz ? renderQuiz(quiz) : <p>퀴즈를 불러오는 중...</p>}
    </Container>
  );
};

export default QuizBody;
