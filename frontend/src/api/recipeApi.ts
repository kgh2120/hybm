import instance from "./axios";

// 위험식품 칸별 조회
const getDangerFoodBySection = async () => {
  try {
    const res = await instance.get("/api/foods/danger");
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

const getRecipeInfo = async (foodId: number) => {
  try {
    const res = await instance.get(
      `/api/recipes/recommend?foodId=${foodId}`
    );
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export { getDangerFoodBySection, getRecipeInfo };
