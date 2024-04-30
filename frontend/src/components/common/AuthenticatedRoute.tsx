import { Navigate, Outlet } from "react-router-dom";
import useAuthStore from "../../stores/useAuthStore";
import styles from "../../styles/common/AuthenticatedRoute.module.css";

function AuthenticatedRoute() {
  const isLogin = useAuthStore((state) => state.isLogin);
  if (isLogin) {
    return <Navigate to="/" />;
  }
  return (
    <div className={styles.wrapper}>
      <Outlet />
    </div>
  );
}

export default AuthenticatedRoute;
