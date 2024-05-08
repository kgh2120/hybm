import instance from "./axios";

// 냉장고 디자인 전체 조회
const getDesignList = async () => {
  try {
    const res = await instance.get("/api/refrigerators/designs");
    console.log(res);
    return res;
  } catch (e) {
    console.log(e);
  }
}

// 적용한 디자인 조회(냉동/냉장/선반)
const getCurrentDesign = async () => {
  try {
    const res = await instance.get("/api/refrigerators/designs/using");
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

export { getDesignList, getCurrentDesign }