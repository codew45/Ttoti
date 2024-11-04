// CharacterSelectPage.tsx
import React, { useState } from 'react';
import styled, { css } from 'styled-components';
import monkey from '@assets/characters/Monkey_portrait.png';
import owl from '@assets/characters/Owl_portrait.png';
import porcupine from '@assets/characters/Porcupine_portrait.png';
import rabbit from '@assets/characters/Rabbit_portrait.png';

const PageContainer = styled.div`
  position: relative;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
`;

const Overlay = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
`;

const ModalContatiner = styled.div`
  display: grid;
  grid-template-rows: 130px 1fr 130px;
  position: relative;
  width: 100vw;
  max-width: 422px;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 1);
`;

const TitleContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 130px;
  box-sizing: border-box;
  border-bottom: 1px solid white;
`;

const TitleText = styled.h1`
  height: 48px;
  font-family: 'SB어그로';
  font-size: 48px;
  font-weight: bold;
`;

const ListContainer = styled.div`
  display: flex;
  flex-direction: column;
  overflow-y: auto;
  white-space: nowrap;
`;

interface CardProps {
  $highlighted: boolean;
}

const Card = styled.div<CardProps>`
  display: flex;
  flex-direction: row;
  height: 130px;
  box-sizing: border-box;
  border-top: 1px solid white;
  border-bottom: 1px solid white;
  cursor: pointer;
  transition: background-color 0.3s;

  ${({ $highlighted }) =>
    $highlighted &&
    css`
      background-color: rgba(255, 255, 255, 0.2);
    `}
`;

const ImageBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 130px;
  height: 130px;
`;

const CharacterImage = styled.img`
  margin-right: 8px;
`;

const CharacterText = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
`;

const Name = styled.h2`
  line-height: 0px;
  font-family: 'GmarketSans';
  font-size: 24px;
  font-weight: normal;
`;

const Description = styled.p`
  font-family: 'LINESeed';
  font-size: 14px;
  font-weight: normal;
`;

const FooterContainer = styled.footer`
  display: flex;
  align-items: center;
  justify-content: center;
  height: 130px;
  box-sizing: border-box;
  border-top: 1px solid white;
`;

const SelectButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 200px;
  height: 54px;
  background-color: #1b95ec;
  color: white;
  border: none;
  border-radius: 50px;
  cursor: pointer;
`;

const SelectText = styled.p`
  height: 32px;
  font-family: 'GmarketSans';
  font-size: 32px;
  font-weight: normal;
`;

const CharacterSelectPage: React.FC = () => {
  const [selectedCardIndex, setSelectedCardIndex] = useState<number | null>(null);

  const characters = [
    { image: monkey, name: '원숭이', description: '안녕, 나는 원숭이 캐릭터야 우꺄!' },
    { image: porcupine, name: '고슴도치', description: '안녕, 나는 고슴도치 캐릭터야 도치!' },
    { image: owl, name: '부엉이', description: '안녕, 나는 부엉이 캐릭터야 부엉!' },
    { image: rabbit, name: '토끼', description: '안녕, 나는 토끼 캐릭터야 총총!' },
    { image: monkey, name: '원숭이', description: '안녕, 나는 원숭이 캐릭터야 우꺄!' },
    { image: porcupine, name: '고슴도치', description: '안녕, 나는 고슴도치 캐릭터야 도치!' },
    { image: owl, name: '부엉이', description: '안녕, 나는 부엉이 캐릭터야 부엉!' },
    { image: rabbit, name: '토끼', description: '안녕, 나는 토끼 캐릭터야 총총!' }
  ];

  const handleCardClick = (index: number) => {
    setSelectedCardIndex(index);
  };

  return (
    <PageContainer>
      <Overlay />
      <ModalContatiner>
        <TitleContainer>
          <TitleText>캐릭터 선택</TitleText>
        </TitleContainer>
        <ListContainer>
          {characters.map((character, index) => (
            <Card
              key={index}
              onClick={() => handleCardClick(index)}
              $highlighted={selectedCardIndex === index}
            >
              <ImageBox>
                <CharacterImage src={character.image} alt={character.name} />
              </ImageBox>
              <CharacterText>
                <Name>{character.name}</Name>
                <Description>{character.description}</Description>
              </CharacterText>
            </Card>
          ))}
        </ListContainer>
        <FooterContainer>
          <SelectButton>
            <SelectText>선택완료</SelectText>
          </SelectButton>
        </FooterContainer>
      </ModalContatiner>
    </PageContainer>
  );
};

export default CharacterSelectPage;
