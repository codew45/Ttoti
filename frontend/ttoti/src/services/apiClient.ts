import axios from 'axios';
const API_URL = import.meta.env.VITE_URL;

// Api 클라이언트 생성 함수
export const createApiClient = (accessToken: string | null) => {
	if (!accessToken) {
		// accessToken 없음 디버깅 콘솔
		console.log('createApiClient : accessToken이 제공되지 않았습니다.');
	}
	return axios.create({
		baseURL: API_URL,
		headers: {
			Authorization: `Bearer ${accessToken}`,
		},
	});
};

// localStorage에서 token 관리
const getAccessToken = () => localStorage.getItem('accessToken');
const getRefreshToken = () => localStorage.getItem('refreshToken');
const setAccessToken = (token: string) =>
	localStorage.setItem('accessToken', token);
const setRefreshToken = (token: string) =>
	localStorage.setItem('refreshToken', token);
const clearAccessToken = () => localStorage.removeItem('accessToken');
const clearRefreshToken = () => localStorage.removeItem('refreshToken');

// login 상태 확인 -> True / False 값 반환
export const isLoggedIn = () => !!getAccessToken();

// API 클라이언트 가져오기

export const getApiClient = () => {
	const accessToken = getAccessToken();
	const refreshToken = getRefreshToken();

	const apiClient = createApiClient(accessToken);
	// interceptors : access token 만료시 refresh token 재발급
	apiClient.interceptors.response.use(
		(response) => {
			return response;
		},
		async (error) => {
			const originalRequest = error.config;
			// ._retry : 재시도 여부 확인
			if (originalRequest._retry) {
				// 재시도를 멈추고 에러를 처리
				console.error(
					'Refresh token request failed again, stopping further requests.',
				);
				return Promise.reject(new Error('Unauthorized, please log in again.'));
			}
			// 401 에러코드 인증되지 않은 사용자 오류
			if (error.response?.data.httpStatus === 401 && !originalRequest._retry) {
				console.log('토큰 만료');
				originalRequest._retry = true;

				try {
					// refreshToken 이 있을 경우
					if (refreshToken) {
						// refeshToken으로 토큰 재발급 요청
						// 경로 반영 예정
						const res = await apiClient.patch('/auth/reissue', {
							refreshToken: refreshToken,
						});
						console.log(res);
						if (res.status == 200) {
							// 다시 localStorage에 토큰 저장
							setAccessToken(res.data.body.accessToken);
							setRefreshToken(res.data.body.refreshToken);
							// 재발급한 토큰으로 실패한 객체 다시 요청
							originalRequest.headers['Authorization'] =
								`Bearer ${getAccessToken()}`;
							return apiClient(originalRequest);
						} else {
							return Promise.reject(
								new Error('Token reissue failed, logging out.'),
							);
						}
					}
				} catch (err) {
					// refresh Token으로 접근하지 못했을 경우
					return Promise.reject(err);
				}
			} else {
				console.log(
					`error status: ${error.response.status}, httpStatus: ${error.response.data.httpStatus}`,
				);
				// refresh Token 처리 후 토큰 재발급 실패 -> 메인 화면으로 돌아가기
				if (error.response.data.httpStatus == 401) {
					clearAccessToken();
					clearRefreshToken();
					const currentURL = window.location.href;
					if (currentURL.includes('localhost')) {
						window.location.href = 'http://localhost:5173/login';
					} else {
						window.location.href = `https://ttoti.co.kr/login`;
					}
				}
				return Promise.reject(new Error('잘못된 axios 요청'));
			}
			// 400 에러 코드 : Bad Request
			if (error.response?.status == 400) {
				console.error('Bad Request (400):', error.response.data);
				return Promise.reject(
					new Error(
						'Request failed with status 400, stopping further requests.',
					),
				);
			}

			return Promise.reject(error);
		},
	);
};
