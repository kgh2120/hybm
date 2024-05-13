import styles from "../../styles/common/DropDown.module.css";
import { useMutation, useQuery } from "@tanstack/react-query";
import { logOut, signOut } from "../../api/userApi";
import useAuthStore from "../../stores/useAuthStore";
import { useState } from "react";
import ConfirmModal from "./ConfirmModal";

function DropDown() {
  const { setIsLogin } = useAuthStore();
  const [isLogOut, setLogout] = useState(false);
  const [isSignOutConfirmModalOpen, setSignOutConfirmModalOpen] =
    useState(false);

  // API 호출
  const {
    data: logOutStatus,
    isPending: isLogOutPending,
    isError: isLogOutError,
  } = useQuery({
    queryKey: ["logOut"],
    queryFn: logOut,
    enabled: isLogOut,
  });

  const { mutate: mutateSignOut } = useMutation({
    mutationFn: signOut,
    onSuccess: () => {
      setIsLogin(false);
    },
  });

  // onClick handle 메서드들
  const handleLogOut = () => {
    setLogout(true);
    if (logOutStatus === 200) {
      setIsLogin(false);
    } else {
      alert("로그아웃에 실패하였습니다.");
    }
  };

  const handleOpenSignOutConfirmModal = () => {
    setSignOutConfirmModalOpen(true);
  };

  const handleSignOut = () => {
    mutateSignOut();
  };

  const closeSingOutConfirmModal = () => {
    setSignOutConfirmModalOpen(false);
  };

  // pending, error 처리
  if (isLogOutPending) {
    return <div>로그아웃 하는 중...</div>;
  }

  if (isLogOutError) {
    return <div>로그아웃 에러...</div>;
  }

  return (
    <div className={styles.wrapper}>
      <button onClick={handleLogOut}>로그아웃</button>
      <p onClick={handleOpenSignOutConfirmModal}>회원탈퇴</p>
      {isSignOutConfirmModalOpen && (
        <ConfirmModal
          content="정말 회원 탈퇴하시겠습니까?"
          option1="예"
          option2="취소"
          option1Event={handleSignOut}
          option2Event={closeSingOutConfirmModal}
        />
      )}
    </div>
  );
}

export default DropDown;
