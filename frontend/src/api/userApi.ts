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
