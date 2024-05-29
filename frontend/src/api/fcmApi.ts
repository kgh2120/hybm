import instance from "./axios";

// FCM 토큰 저장
const saveFcmToken = async (token: string) => {
  try {
    const res = await instance.post("/api/notices/fcm", { token });
    return res.status;
  } catch (e) {
    console.log(e);
  }
};

// FCM 메시지 수신
const receiveFcmMessage = async () => {
  try {
    const res = await instance.get("/api/notices/fcm-message");
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export { saveFcmToken, receiveFcmMessage };
