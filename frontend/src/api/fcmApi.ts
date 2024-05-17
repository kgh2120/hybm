import instance from "./axios";

// FCM 토큰 저장
const saveFcmToken = async (token: string) => {
  try {
    const res = await instance.post("/api/notices/fcm", { token });
    return res.status;
  } catch (e) {
    console.log(e);
    throw e;
  }
};

export { saveFcmToken };
