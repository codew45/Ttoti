// App.tsx
import MyButton from './components/Button/MyButton'; // MyButton 컴포넌트 가져오기

const App = () => {
  // 버튼 클릭 시 실행될 함수
  const handleClick = () => {
    console.log('버튼이 클릭되었습니다!'); // 클릭 시 콘솔에 메시지 출력
  };

  return (
    <div>
      <h1>MyButton 사용 예제</h1>
      <MyButton 
        label="Primary Button" // 버튼에 표시될 텍스트
        backgroundColor="#007bff" // 버튼 배경색 설정
        onClick={handleClick} // 클릭 시 실행될 함수 설정
        size="medium" // 버튼 크기 설정
      />
      <br />
      <MyButton 
        label="Secondary Button"
        backgroundColor="#6c757d" 
        onClick={handleClick}
        size="large" // 다른 크기 설정
      />
      <br />
      <MyButton 
        label="Small Button"
        backgroundColor="#28a745" 
        onClick={handleClick}
        size="small" // 작은 버튼 설정
      />
    </div>
  );
};

export default App;