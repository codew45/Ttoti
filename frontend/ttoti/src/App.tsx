import { ThemeProvider } from 'styled-components';
import { theme } from '@styles/theme';
import { BrowserRouter } from 'react-router-dom';
import AppRouter from '@routes/AppRouter';
import 'normalize.css'

const App = () => {
	return (
		<ThemeProvider theme={theme}>
			<BrowserRouter>
				<AppRouter />
			</BrowserRouter>
		</ThemeProvider>
	);
};

export default App;
