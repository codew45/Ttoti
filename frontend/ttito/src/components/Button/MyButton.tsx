// components/Button/MyButton.tsx
export interface MyButtonProps {
  label: string;
  backgroundColor?: string;
  onClick?: () => void;
  size?: 'small' | 'medium' | 'large'; // 사이즈 prop 추가
}

const MyButton = ({ 
  label, 
  backgroundColor = '#007bff', 
  onClick, 
  size = 'medium' 
}: MyButtonProps) => {
  const paddingSize = size === 'small' 
  ? '5px 10px' 
  : size === 'large' 
  ? '15px 30px' 
  : '10px 20px';

  return (
    <button
      style={{
        backgroundColor,
        color: '#fff',
        padding: paddingSize,
        border: 'none',
        borderRadius: '4px',
        cursor: 'pointer',
      }}
      onClick={onClick}
    >
      {label}
    </button>
  );
};

export default MyButton;