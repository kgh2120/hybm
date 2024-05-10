import instance from "./axios";

// 위험식품 칸별 조회
const getDangerFoodBySection = async () => {
  try {
    const res = await instance.get("/api/foods/danger");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export { getDangerFoodBySection };
