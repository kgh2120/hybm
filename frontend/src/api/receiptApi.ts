import instance from "./axios";

// 영수증 OCR 요청
const postReceipt = async (image: File | null) => {
  const formData = new FormData();
  formData.append("image", image!);
  console.log(image);
  try {
    const res = await instance.post(
      "/api/foods/getReceiptOCR",
      formData
    );
    alert(`테스트: ${image}, ${formData}, ${res}`);
    return res.data;
  } catch (e) {
    alert(`실패: ${image}, ${formData}, ${e}`);
    console.log(e);
  }
};

interface FoodDataType {
  name: string;
  categoryId: number;
  price: number;
  expiredDate: string;
  location: string;
}

const postFoodByReceipt = async (foodList: FoodDataType[]) => {
  try {
    const res = await instance.post("/api/foods/bill", foodList);
    return res.data;
  } catch (e) {
    console.log(e);
    throw e;
  }
};

export { postReceipt, postFoodByReceipt };
