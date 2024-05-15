import instance from "./axios";

// 영수증 OCR 요청
const postReceipt = async (imagePath: string) => {
    console.log(imagePath);
  try {
    const res = await instance.post("/api/foods/getReceiptOCR", imagePath);
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export { postReceipt };
