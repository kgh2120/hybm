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

// 추천 레시피 조회
const getRecipeInfo = async (foodIdList: number[]) => {
  try {
    const foodIdsString = foodIdList.join(",");
    const res = await instance.get(
      `/api/recipes/recommend?foodId=${foodIdsString}`
    );
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export { getDangerFoodBySection, getRecipeInfo };
