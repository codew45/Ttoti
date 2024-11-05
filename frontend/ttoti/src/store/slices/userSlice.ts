import { createSlice, PayloadAction } from '@reduxjs/toolkit';
import { RootState } from '../index';

interface UserState {
  name: string;
}

const initialState: UserState = {
  name: '정진영', // 초기값 설정
};

const userSlice = createSlice({
  name: 'user',
  initialState,
  reducers: {
    setName(state, action: PayloadAction<string>) {
      state.name = action.payload;
    },
  },
});

export const { setName } = userSlice.actions;
export default userSlice.reducer;

export const selectUserName = (state: RootState) => state.user.name;
