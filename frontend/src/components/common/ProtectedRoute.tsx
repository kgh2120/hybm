import { Navigate, Outlet } from 'react-router-dom';
import useAuthStore from '../../stores/useAuthStore';

function ProtectedRoute() {
  const isLogin = useAuthStore((state) => state.isLogin);

  if (isLogin) {
    return <Navigate to='/landing' />;
  }
  return (
    <div>
      <Outlet />
    </div>
  );
}

export default ProtectedRoute;
