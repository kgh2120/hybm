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

// 로그아웃
const logOut = async () => {
  try {
    const res = await instance.get("/api/users/logout");
    return res.status;
  } catch (e) {
    console.log(e);
  }
};

// 회원탈퇴

const signOut = async () => {
  try {
    const res = await instance.delete("/api/users/sign-out");
    return res.status;
  } catch (e) {
    console.log(e);
    throw e;
  }
};

export {
  getLoginStatus,
  getUserSignUpDate,
  getLevelAndExp,
  logOut,
  signOut,
};
