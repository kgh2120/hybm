import instance from "./axios";

// 식품 분류 조회
const getBigCategoryList = async () => {
  try {
    const res = await instance.get("/api/foods/category");
    return res.data;
  } catch (e) {
    console.error(e);
  }
};

// 내부 식품 칸별 조회
const getFoodStorageItemList = async (storageName: string) => {
  try {
    const res = await instance.get(
      `/api/foods/storage/${storageName}`
    );
    return res.data;
  } catch (e) {
    console.error(e);
  }
};

interface DeleteFoodParams {
  foodIdList: number[];
  option: string;
}

// 먹음/버림
const deleteFood = async ({
  foodIdList,
  option,
}: DeleteFoodParams) => {
  try {
    const foodIdsString = foodIdList.join("&foodId=");
    const res = await instance.delete(
      `/api/foods/${option}?foodId=${foodIdsString}`
    );
    return res.data;
  } catch (e) {
    console.error(e);
  }
};

// 냉텅텅(전부삭제)
const deleteAllFood = async () => {
  try {
    const res = await instance.delete("/api/foods/clear");
    return res.data;
  } catch (e) {
    console.error(e);
  }
};

// 소비기한 확인
const getExpiredDate = async (categoryId: number) => {
  const currentDate = new Date();
  const year = currentDate.getFullYear();
  const month = String(currentDate.getMonth() + 1).padStart(2, "0");
  const day = String(currentDate.getDate()).padStart(2, "0");
  try {
    const res = await instance.get(
      `/api/foods/expiration?categoryDetailId=${categoryId}&&year=${year}&&month=${month}&&day=${day}`
    );
    return res.data;
  } catch (e) {
    console.error(e);
  }
};

// 식품 등록
interface FoodDataType {
  name: string;
  categoryId: number;
  price: number;
  expiredDate: string;
  location: string;
  isManual: boolean;
}
const postFood = async (foodData: FoodDataType) => {
  try {
    const res = await instance.post("/api/foods", foodData, {});
    return res
  } catch (e) {
    console.error("식품 등록 실패:", e);
    throw e;
  }
};

// 내부 식품 상세 조회
const getFoodDetail = async (foodId: number) => {
  try {
    const res = await instance.get(`/api/foods/${foodId}`);
    return res.data;
  } catch (e) {
    console.error(e);
  }
};

// 내부 식품 수정
interface FoodEditDataType {
  name: string;
  categoryId: number;
  price: number;
  expiredDate: string;
  location: string;
}

interface FoodDetailType {
  foodId: number;
  foodEditData: FoodEditDataType;
}

const putFoodDetail = async ({ foodId, foodEditData }: FoodDetailType) => {
  try {
    const res = await instance.put(
      `/api/foods/${foodId}`,
      foodEditData
    );
    return res.data;
  } catch (e) {
    console.log(e);
    throw e;
  }
};

// 바코드 정보 조회
const getBarcodeData = async (barcode: number) => {
  try {
    const res = await instance.get(`/api/foods?barcode=${barcode}`);
    alert(`getBarcode 성공: ${res.data}`
    )
    return res.data;
  } catch (e) {
    alert(`getBarcode error: ${e}`)

    console.error(e);
  }
};

export {
  getFoodStorageItemList,
  getBigCategoryList,
  deleteFood,
  deleteAllFood,
  getExpiredDate,
  postFood,
  getFoodDetail,
  putFoodDetail,
  getBarcodeData
};
