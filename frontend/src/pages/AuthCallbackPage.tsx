import { useNavigate } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getLoginStatus } from "../api/userApi";
import useAuthStore from "../stores/useAuthStore";

function AuthCallback() {
  const { setIsLogin } = useAuthStore();
  const navigate = useNavigate();
  const {
    data: loginStatus,
    isPending: isLoginStatusPending,
    isError: isLoginStatusError,
  } = useQuery({
    queryKey: ["loginStatus"],
    queryFn: getLoginStatus,
  });

  if (isLoginStatusPending) {
    return <div>로그인 중...</div>;
  }
  if (isLoginStatusError) {
    navigate("/landing");
  }
  if (loginStatus === 200) {
    setIsLogin(true);
    navigate("/");
  }
  return <></>;
}

export default AuthCallback;
