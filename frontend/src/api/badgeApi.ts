import instance from "./axios";

export async function getCurrentBadgeList() {
  try {
    const res = await instance.get("/api/refrigerators/badges/attached");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}
