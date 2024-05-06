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

export async function getExpiredDate(categoryId: number) {
  const currentDate = new Date();
const year = currentDate.getFullYear();
const month = String(currentDate.getMonth() + 1).padStart(2, '0');
const day = String(currentDate.getDate()).padStart(2, '0');
  try {
    const res = await instance.get(`/api/foods/expiration?categoryDetailId=${categoryId}&&year=${year}&&month=${month}&&day=${day}`);
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}
