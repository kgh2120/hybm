import Button from "../common/Button";
import styles from "../../styles/storagePage/CreateFoodModal.module.css";
import FoodSection from "../common/FoodSection";
import useFoodStore from "../../stores/useFoodStore";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { getFoodDetail, putFoodDetail } from "../../api/foodApi";
import { useEffect, useState } from "react";
import { formatDashDate, formatDashStringToDate } from "../../utils/formatting";
import LoadingSpinner from "../common/LoadingSpinner";

interface FoodDetailModalProps {
  foodId: number;
  handleCloseFoodDetailModal: () => void;
}

function FoodDetailModal({
  foodId,
  handleCloseFoodDetailModal,
}: FoodDetailModalProps) {
  const queryClient = useQueryClient();
  const { inputList, initInputList, setInputList } =
    useFoodStore();

  const [foodEditData, setFoodEditData] = useState({
    name: "",
    categoryId: 0,
    price: 0,
    expiredDate: "",
    location: "",
  });
  const isEnglishLocation = ["ICE", "COOL", "CABINET"].includes(
    inputList.location
  );
  const LOCATION_LIST: { [key: string]: string } = {
    냉동칸: "ICE",
    냉장칸: "COOL",
    찬장: "CABINET",
  };
  
  useEffect(() => {
    setFoodEditData({
      name: inputList.foodName,
      categoryId: inputList.categoryId,
      price: inputList.price,
      expiredDate: formatDashDate(inputList.expiredDate),
      location: isEnglishLocation
        ? inputList.location
        : LOCATION_LIST[inputList.location],
    });
  }, [inputList]);

  // 내부 식품 수정 api
  const { mutate: mutateFoodDetail } = useMutation({
    mutationFn: putFoodDetail,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["foodStorageItemList"],
      });
      handleCloseFoodDetailModal();
      initInputList();
    },
  });
  const handleEditFood = () => {
    mutateFoodDetail({ foodId, foodEditData });
  };

  // 내부 식품 상세 조회 api
  const { data: foodDetail, status } = useQuery({
    queryKey: ["foodDetail"],
    queryFn: () => getFoodDetail(foodId),
    gcTime: 0,
  });

  // useEffect(() => {
  //   if (isFoodDetailModal && foodId) {
  //     refetch();
  //   }
  // }, [isFoodDetailModal, foodId, refetch]);

  useEffect(() => {
    if (foodDetail) {
      const newDate = formatDashStringToDate(foodDetail.expiredDate);
      setInputList({
        foodName: foodDetail.name,
        categoryId: foodDetail.categoryId,
        categoryBigId: foodDetail.bigCategoryId,
        categoryImgSrc: foodDetail.categoryImgSrc,
        expiredDate: {
          year: newDate.year,
          month: newDate.month,
          day: newDate.day,
        },
        price: foodDetail.price,
        location: foodDetail.location,
      });
      setTimeout(() => {
      }, 3000);
    }
  }, [foodDetail, foodId]);

  if (status === "pending") {
    return <LoadingSpinner />
  }
  return (
    <div className={styles.wrapper}>
      <section className={styles.main_section}>
        <FoodSection option="detail" />
        {/* <section className={styles.btn_section}>
          <div>
            <img src={barcode} alt="바코드아이콘" />
          </div>
          <div>
            <img src={camera} alt="카메라아이콘" />
          </div>
          <div></div>
        </section> */}
      </section>
      <span>
        * 분류에 따른 <span>예상 소비기한</span>이 제공되나
        <br />
        상이할 수 있습니다.
      </span>
      <Button
        content="수정"
        color="red"
        onClick={handleEditFood}
        disabled={
          foodEditData.name == "" || foodEditData.categoryId == 0
        }
      />
    </div>
  );
}

export default FoodDetailModal;
