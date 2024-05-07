import instance from "./axios";

// 부착한 배지 조회
const getCurrentBadgeList = async () => {
  try {
    const res = await instance.get("/api/refrigerators/badges/attached");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

export { getCurrentBadgeList }