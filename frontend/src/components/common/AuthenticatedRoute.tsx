import { Navigate, Outlet } from 'react-router-dom'
import useAuthStore from '../../stores/useAuthStore'

function AuthenticatedRoute() {
  const isLogin = useAuthStore((state) => state.isLogin)
  if (isLogin) {
    return <Navigate to='/' />
  }
  return (
    <div><Outlet /></div>
  )
}

export default AuthenticatedRoute