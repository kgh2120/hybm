import instance from "./axios";

// 부착한 배지 조회
const getCurrentBadgeList = async () => {
  try {
    const res = await instance.get(
      "/api/refrigerators/badges/attached"
    );
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

// 배지 전체 조회
const getBadgeList = async () => {
  try {
    const res = await instance.get("/api/refrigerators/badges");
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

interface PutBadgePositionParams {
  badgeId: number;
  position: number | null;
}

// 배지 위치 변경
const putBadgePosition = async (
  badgeListParams: PutBadgePositionParams[]
) => {
  try {
    const res = await instance.put(
      "/api/refrigerators/badges",
      badgeListParams
    );
    return res.data;
  } catch (e) {
    console.log(e);
    throw e;
  }
};
export { getCurrentBadgeList, getBadgeList, putBadgePosition };
