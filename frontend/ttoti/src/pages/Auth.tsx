import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Loader from '@components/common/Loder';

const Auth = () => {
	const navigate = useNavigate();
	const url = new URL(window.location.href);
	const accessToken = url.searchParams.get('accessToken');
	const refreshToken = url.searchParams.get('refreshToken');

	useEffect(() => {
		const fetchUserState = async () => {
			if (accessToken && accessToken.length > 0 && refreshToken) {
				try {
					// local에 저장
					localStorage.setItem('accessToken', accessToken);
					localStorage.setItem('refreshToken', refreshToken);

					// 메인 페이지로 이동
					navigate('/');
				} catch (error) {
					console.log('fetchUserState Error : ', error);
				}
			} else {
				console.log('인증 실패');
			}
		};

		fetchUserState();
	}, [accessToken, refreshToken]);

	return <Loader />;
};

export default Auth;
