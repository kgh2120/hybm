import styles from "../../styles/common/DropDown.module.css";
import { useQuery } from "@tanstack/react-query";
import { logOut } from "../../api/userApi";
import useAuthStore from "../../stores/useAuthStore";
import { useState } from "react";
import LoadingSpinner from "./LoadingSpinner";

interface DropDownProps {
  openModal: () => void;
}

function DropDown({ openModal }: DropDownProps) {
  const { setIsLogin } = useAuthStore();
  const [isLogOut, setLogout] = useState(false);

  // API 호출
  const {
    isLoading: isLogOutLoading,
    isError: isLogOutError,
    status: logOutStatus,
  } = useQuery({
    queryKey: ["logOut"],
    queryFn: logOut,
    enabled: isLogOut,
  });

  // onClick handle 메서드들
  const handleLogOut = () => {
    setLogout(true);
    if (logOutStatus) {
      setIsLogin(false);
      setLogout(false);
    }
  };

  const handleOpenSignOutConfirmModal = () => {
    openModal();
  };

  // pending, error 처리
  if (isLogOutLoading) {
    return <LoadingSpinner />
  }

  if (isLogOutError) {
    return <div>로그아웃 에러...</div>;
  }

  return (
    <div className={styles.wrapper}>
      <button onClick={handleLogOut}>로그아웃</button>
      <p onClick={handleOpenSignOutConfirmModal}>회원탈퇴</p>
    </div>
  );
}

export default DropDown;
