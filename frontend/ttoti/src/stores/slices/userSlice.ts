import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../index';
import fetchUserInfo from '@hooks/fetchUserInfo';

interface UserState {
	memberId: string;
	memberName: string;
	memberProfileImageUrl: string;
}

const initialState: UserState = {
	memberId: '',
	memberName: '',
	memberProfileImageUrl: '',
};

const userSlice = createSlice({
	name: 'user',
	initialState,
	reducers: {
		setUserInfo(
			state,
			action: PayloadAction<{
				memberId: string;
				memberName: string;
				memberProfileImageUrl: string;
			}>,
		) {
			state.memberId = action.payload.memberId;
			state.memberName = action.payload.memberName;
			state.memberProfileImageUrl = action.payload.memberProfileImageUrl;
		},
	},
	extraReducers: (builder) => {
		builder.addCase(fetchUserInfo.fulfilled, (state, action) => {
			if (action.payload) {
				state.memberId = action.payload.memberId;
				state.memberName = action.payload.memberName;
				state.memberProfileImageUrl = action.payload.memberProfileImageUrl;
			}
		});
	},
});

export const { setUserInfo } = userSlice.actions;
export default userSlice.reducer;

// store 조회
export const selectMemberName = (state: RootState) => state.user.memberName;
export const selectMemberId = (state: RootState) => state.user.memberId;
export const selectMemberProfile = (state: RootState) =>
	state.user.memberProfileImageUrl;
