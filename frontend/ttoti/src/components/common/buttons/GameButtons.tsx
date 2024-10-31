import styled from 'styled-components';
import { ColorProps } from 'src/types/ColorsTheme';

const GameButtons = styled.button<ColorProps>`
	display: flex;
	flex-direction: row;
	justify-content: center;
	align-items: center;
	padding: 5px 24px;
	width: 120px;
	height: 35px;
	box-sizing: border-box;
	border: 0px solid transparent;
	border-radius: 15px;
	font-family: 'GmarketSans';
	font-size: 16px;
	font-weight: bold;
	text-align: center;
	cursor: pointer;
	background-color: ${({ theme, color }) => theme.colors[color]};
	color: white;
`;

export default GameButtons;
