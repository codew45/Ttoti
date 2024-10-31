import theme from '@styles/theme';

export interface ModalTitleProps {
	subtitleText: string;
	titleText: string;
}

export interface ModalButtonProps {
	explainText: string;
	buttonColor1: keyof typeof theme.colors;
	buttonText1: string;
	buttonColor2: keyof typeof theme.colors;
	buttonText2: string;
}
