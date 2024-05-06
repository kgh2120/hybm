import instance from './axios';

export async function getBigCategoryList() {
  try {
    // const res = await instance.get('/foods/category', {});
    const res = await instance.get("/api/foods/category");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

interface DeleteFoodParams {
  foodIdList: number[];
  option: string;
}

export async function deleteFood({foodIdList, option}: DeleteFoodParams) {
  try {
    const foodIdsString = foodIdList.join('&foodId=');
    const res = await instance.delete(`/api/foods/${option}?foodId=${foodIdsString}`);
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

export async function deleteAllFood() {
  try {
    const res = await instance.delete("/api/foods/clear");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

