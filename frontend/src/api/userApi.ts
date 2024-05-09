import instance from "./axios";

// 로그인 체크
const getLoginStatus = async () => {
  try {
    const res = await instance.get("/api/users/is-login");
    return res.status;
  } catch (e) {
    console.log(e);
  }
};

// 가입한 년도와 월 조회
const getUserSignUpDate = async () => {
  try {
    const res = await instance.get("/api/users/is-login");
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

// 레벨/경험치 조회
const getLevelAndExp = async () => {
  try {
    const res = await instance.get("/api/levels");
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export { getLoginStatus, getUserSignUpDate, getLevelAndExp };
