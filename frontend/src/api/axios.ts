import axios from "axios";
import useAuthStore from "../stores/useAuthStore";
import { useNavigate } from "react-router-dom";

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
    console.log("에러", err);
    if (err.response.status === 401) {
      console.log("여기입니다2");
      try {
        // 토큰 재발행을 했을 때 뭔 코드이면 토큰 재발행 API를 쏜다!
        await axios.get(`${VITE_API_DEV}/api/users/reissue`);

        // 에러가 아니라 성공이다! 그럼 원래 요청을 다시 보내준다!
        return instance(err.config);
      } catch (e) {
        // 그 결과가 에러다. 그럼 아래 IF문을 완성한다.
        if (err.response.data.errName === "REFRESH_TOKEN_MISSING") {
          alert(err.response.data.errMessage);

          // 1. 상태 바꿔주기
          const { setIsLogin } = useAuthStore();
          setIsLogin(false);

          // 2. 로그인 페이지로 보내주기
          const navigate = useNavigate();
          navigate(VITE_RELOGIN_URI_BASE);
        }
      }
    }
  }
);

export default instance;
