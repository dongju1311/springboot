import { createSlice } from '@reduxjs/toolkit'

const saveAuth = JSON.parse(localStorage.getItem("auth"));

const initialState = saveAuth || {
  isLogin: false
}

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
        login(state, action) {
            state.isLogin = !state.isLogin;
            const { userId } = action.payload;
            const loginInfo = { "userId": userId};
            localStorage.setItem("loginInfo", JSON.stringify(loginInfo));

            //새로고침을 위한 데이터 복사(localStrorage 저장)
            localStorage.setItem("auth",
                                 JSON.stringify({
                                     isLogin : true,
                                     userId
                                 })
            )
        },
        logout(state, action) {
            state.isLogin = !state.isLogin;
            //로컬스토리지 정보 삭제
            localStorage.removeItem("loginInfo");
            localStorage.removeItem("auth");
            localStorage.removeItem("cart");
        }
  },
})

export const { login, logout } 
    = authSlice.actions   //API 함수 또는 컴포넌트에서 dispatch(액션함수)

export default authSlice.reducer  //store  import