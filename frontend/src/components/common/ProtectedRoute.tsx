import { Navigate, Outlet } from "react-router-dom";
import useAuthStore from "../../stores/useAuthStore";
import styles from "../../styles/common/ProtectedRoute.module.css";

function ProtectedRoute() {
  const isLogin = useAuthStore((state) => state.isLogin);

  if (isLogin) {
    return <Navigate to="/landing" />;
  }
  return (
    <div className={styles.wrapper}>
      <Outlet />
    </div>
  );
}

export default ProtectedRoute;
