import instance from "./axios";

// 영수증 OCR 요청
const postReceipt = async (image: string) => {
  const byteCharacters = atob(image); // Base64 디코딩
  const byteNumbers = new Array(byteCharacters.length);
  for (let i = 0; i < byteCharacters.length; i++) {
    byteNumbers[i] = byteCharacters.charCodeAt(i);
  }
  const byteArray = new Uint8Array(byteNumbers);
  const blob = new Blob([byteArray], { type: "image/jpeg" });

  const formData = new FormData();
  formData.append("image", blob);

  try {
    const res = await instance.post(
      "/api/foods/getReceiptOCR",
      formData
    );
    return res.data.receiptProducts;
  } catch (e) {
    console.log(e);
    throw e;
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
