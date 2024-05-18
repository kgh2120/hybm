import { useNavigate } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getLoginStatus } from "../api/userApi";
import useAuthStore from "../stores/useAuthStore";
import LoadingSpinner from "../components/common/LoadingSpinner";

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
    return <LoadingSpinner />;
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
