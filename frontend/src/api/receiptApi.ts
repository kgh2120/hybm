import instance from "./axios";

// 영수증 OCR 요청
const postReceipt = async (image: string | null) => {
  const base64ToBlob = (image: string, mimeType: string) => {
    const byteCharacters = atob(image); // base64 디코딩
    const byteArrays = [];
  
    for (let offset = 0; offset < byteCharacters.length; offset += 512) {
      const slice = byteCharacters.slice(offset, offset + 512);
  
      const byteNumbers = new Array(slice.length);
      for (let i = 0; i < slice.length; i++) {
        byteNumbers[i] = slice.charCodeAt(i);
      }
  
      const byteArray = new Uint8Array(byteNumbers);
      byteArrays.push(byteArray);
    }
  
    return new Blob(byteArrays, { type: mimeType }); // Blob 객체 생성
  };

  const blob = base64ToBlob(image!, 'image/jpeg');
  const file = new File([blob], 'image.jpg', { type: 'image/jpeg' });

  const formData = new FormData();
  formData.append("image", file);
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
