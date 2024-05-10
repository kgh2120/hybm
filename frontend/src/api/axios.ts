/* eslint-disable */
import axios from "axios";

const { VITE_API_DEV, VITE_RELOGIN_URI_BASE } = import.meta.env;

const instance = axios.create({
  baseURL: VITE_API_DEV,
  withCredentials: true,
});

instance.interceptors.response.use(
  (res) => {
    return res;
  },
  async (err) => {
    if (err.response.status === 401) {
      if (err.response.data.errorName === "UNAUTHORIZED_ACCESS") {
        try {
          // 토큰 재발행 API
          const res = await instance.post("/api/users/reissue");
          console.log("res", res);
          return instance(err.config);
        } catch (e) {
          // @ts-ignore
          if (e.response.data.errorName === "REFRESH_TOKEN_MISSING") {
            alert("토큰이 만료되었습니다. 다시 로그인해주세요.");
            localStorage.removeItem("userData");
            window.location.href = VITE_RELOGIN_URI_BASE;
          }
        }
      }
    }
    return Promise.reject(err);
  }
);

export default instance;
