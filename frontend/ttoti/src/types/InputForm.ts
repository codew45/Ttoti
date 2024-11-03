export interface FormData {
	roomName: string;
	RoomParticipants: number;
	RoomPeriod: number;
	RoomTime: string; // 시간 형식으로, 예: "18:30:00"
}

export interface ContentProps {
	index: number;
	formData: FormData;
	onInputChange: (name: keyof FormData, value: string | number) => void;
}
