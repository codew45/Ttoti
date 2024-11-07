import { getApiClient } from './apiClient';
interface RoomTotalProps {
	formData: {
		name: string;
		participants: number;
		period: number;
		finishTime: string;
	};
}

const postRoomCreate = async ({ formData }: RoomTotalProps) => {
	const apiClient = getApiClient();
	try {
		const res = await apiClient.post('/rooms', formData, {
			headers: {
				'Content-Type': 'application/json; charset=utf8',
			},
		});
		if (res.status === 200) {
			// 임시 콘솔 및 메인 페이징 ㅣ동
			console.log('방 생성 완료!');
			return 'complete';
		}
	} catch (error) {
		console.log('postRoomCreate Error : ', error);
		throw error;
	}
};

export default postRoomCreate;
