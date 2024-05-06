import instance from "./axios";

export async function getLoginStatus() {
  try {
    const res = await instance.get("/api/users/is-login");
    console.log(res);
    return res.status;
  } catch (e) {
    console.log(e);
  }
}

export async function getLevelAndExp() {
  try {
    const res = await instance.get("/api/levels");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}
