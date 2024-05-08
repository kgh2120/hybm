import instance from "./axios";

// 로그인 status 조회
const getLoginStatus = async () => {
  try {
    const res = await instance.get("/api/users/is-login");
    console.log(res);
    return res.status;
  } catch (e) {
    console.log(e);
  }
};

// 가입한 년도와 월 조회
const getUserSignUpDate = async () => {
  try {
    const res = await instance.get("/api/users/is-login");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export { getLoginStatus, getUserSignUpDate };
