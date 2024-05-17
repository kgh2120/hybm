import { useEffect } from 'react';
import { useMutation } from "@tanstack/react-query";
import { getMessaging, getToken, onMessage } from "firebase/messaging";
import { saveFcmToken } from '../api/fcmApi';
import styles from "../styles/FCMPage.module.css";
import Button from "../components/common/Button";
import { Link, useNavigate } from "react-router-dom";
import './firebase'; // Firebase 초기 설정

function FCMPage() {
  const navigate = useNavigate();

  // FCM 토큰 저장을 위한 mutation
  const { mutate: mutateSaveFcmToken, isLoading: isSavingToken } = useMutation({
    mutationFn: saveFcmToken,
    onSuccess: (status) => {
      if (status === 201) {
        console.log('Token stored successfully');
      } else {
        console.log('Failed to store token');
      }
    },
    onError: (error) => {
      console.log('Error saving token:', error);
    }
  });

  useEffect(() => {
    const messaging = getMessaging();

    // FCM 토큰 가져오기 및 저장
    getToken(messaging, { vapidKey: 'YOUR_VAPID_KEY' }).then((currentToken) => {
      if (currentToken) {
        mutateSaveFcmToken(currentToken);
      } else {
        console.log('No registration token available. Request permission to generate one.');
      }
    }).catch((err) => {
      console.log('An error occurred while retrieving token. ', err);
    });

    // 메시지 수신 리스너 설정
    onMessage(messaging, (payload) => {
      console.log('Message received. ', payload);
      // 메시지 처리 로직 추가
    });
  }, [mutateSaveFcmToken]);

  return (
    <div className={styles.wrapper}>
      <h2>FCM 설정</h2>
      <div className={styles.button_box}>
        <Button content="홈으로" color="red" onClick={() => navigate("/")} disabled={isSavingToken}/>
        <Link to="/">
          <Button content="다른 페이지로" color="white" onClick={() => {}} disabled={false}/>
        </Link>
      </div>
    </div>
  );
}

export default FCMPage;
