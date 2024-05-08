import instance from './axios';

// 식품 분류 조회
const getBigCategoryList = async () => {
  try {
    const res = await instance.get("/api/foods/category");
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

// 내부 식품 칸별 조회
const getFoodStorageItemList = async (storageName: string) => {
  try {
    const res = await instance.get(`/api/foods/storage/${storageName}`);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

interface DeleteFoodParams {
  foodIdList: number[];
  option: string;
}

// 먹음/버림
const deleteFood = async ({foodIdList, option}: DeleteFoodParams) => {
  try {
    const foodIdsString = foodIdList.join('&foodId=');
    const res = await instance.delete(`/api/foods/${option}?foodId=${foodIdsString}`);
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

// 냉텅텅(전부삭제)
const deleteAllFood = async () => {
  try {
    const res = await instance.delete("/api/foods/clear");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

// 소비기한 확인
const getExpiredDate = async (categoryId: number) => {
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

export { getFoodStorageItemList, getBigCategoryList, deleteFood, deleteAllFood, getExpiredDate }
