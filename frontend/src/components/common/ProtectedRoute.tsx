import { useEffect } from "react";
import { Navigate, Outlet } from "react-router-dom";
import useAuthStore from "../../stores/useAuthStore";
import { useMutation } from "@tanstack/react-query";
import { getToken, onMessage } from "firebase/messaging";
import { saveFcmToken } from '../../api/fcmApi';
import { messaging } from '../../firebase';
import styles from "../../styles/common/ProtectedRoute.module.css";

const { VITE_FIREBASE_VAPID_KEY } = import.meta.env;

function ProtectedRoute() {
  const isLogin = useAuthStore((state) => state.isLogin);

  const { mutate: mutateSaveFcmToken } = useMutation({
    mutationFn: saveFcmToken,
    onSuccess: (status) => {
      if (status === 201) {
        console.log('성공');
      }
    },
    onError: (error) => {
      console.log('실패...', error);
    }
  });

  useEffect(() => {
    if (isLogin) {
      // FCM 토큰 가져오기 및 저장
      getToken(messaging, { vapidKey: VITE_FIREBASE_VAPID_KEY })
        .then((currentToken) => {
          if (currentToken) {
            mutateSaveFcmToken(currentToken);
            console.log(currentToken)
          } else {
            console.log('토큰이 없는건가???');
          }
        }).catch((err) => {
          console.log('파베 겟토큰이 안됨 ->', err);
        });

      // 메시지 수신
      onMessage(messaging, (payload) => {
        console.log('메시지 수신:', payload);
      });
    }
  }, [isLogin, mutateSaveFcmToken]);

  if (!isLogin) {
    return <Navigate to="/landing" />;
  }
  
  return (
    <div className={styles.wrapper}>
      <Outlet />
    </div>
  );
}

export default ProtectedRoute;
