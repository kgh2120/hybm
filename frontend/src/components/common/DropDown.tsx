import styles from "../../styles/common/DropDown.module.css";
import { useQuery } from "@tanstack/react-query";
import { logOut, signOut } from "../../api/userApi";
import useAuthStore from "../../stores/useAuthStore";

function DropDown() {
  const { setIsLogin } = useAuthStore();

  // API 호출
  const {
    data: logOutStatus,
    isPending: isLogOutPending,
    isError: isLogOutError,
  } = useQuery({
    queryKey: ["logOut"],
    queryFn: logOut,
  });

  const {
    data: signOutStatus,
    isPending: isSignOutPending,
    isError: isSignOutError,
  } = useQuery({
    queryKey: ["signOut"],
    queryFn: signOut,
  });

  // onClick handle 메서드들
  const handleLogOut = async () => {
    if (logOutStatus === 200) {
      setIsLogin(false);
    } else {
      alert("로그아웃에 실패하였습니다.");
    }
  };

  const handleSignOut = async () => {
    const confirmed = window.confirm("정말 회원탈퇴 하시겠습니까?");
    if (confirmed) {
      if (signOutStatus === 200) {
        alert("회원탈퇴가 완료되었습니다.");
        setIsLogin(false);
        console.log();
      } else {
        alert("회원탈퇴에 실패하였습니다.");
      }
    }
  };

  // pending, error 처리
  if (isLogOutPending) {
    return <div>로그아웃 하는 중...</div>;
  }

  if (isLogOutError) {
    return <div>로그아웃 에러...</div>;
  }

  if (isSignOutPending) {
    return <div>회원탈퇴 하는 중...</div>;
  }
  
  if (isSignOutError) {
    return <div>회원탈퇴 에러...</div>;
  }

  return (
    <div className={styles.wrapper}>
      <button onClick={handleLogOut}>로그아웃</button>
      <p onClick={handleSignOut}>회원탈퇴</p>
    </div>
  );
}

export default DropDown;
