import styled from 'styled-components';
import { SizeProps } from 'src/types/CircleSize';

const ProfileCircle = styled.div<SizeProps>`
  display: flex;
  justify-content: center;
  align-items: center;
  width: ${({ size }) => size};
  height: ${({ size }) => size};
  background-color: white;
  border-radius: 50%;
`

const ProfileImage = styled.img`
  width: 92%;
  height: 92%;
  border-radius: 50%;
`;

interface ProfileContainerProps {
  src: string;
  size: string;
}

const ProfileContainer: React.FC<ProfileContainerProps> = ({ src, size }) => {
  return (
    <ProfileCircle size={size}>
      <ProfileImage src={src} alt='Profile' />
    </ProfileCircle>
  );
};

export default ProfileContainer;