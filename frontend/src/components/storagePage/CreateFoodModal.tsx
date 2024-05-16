/* eslint-disable */
import Button from "../common/Button";
import styles from "../../styles/storagePage/CreateFoodModal.module.css";
import FoodSection from "../common/FoodSection";
import useFoodStore from "../../stores/useFoodStore";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { postFood } from "../../api/foodApi";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { formatDashDate } from "../../utils/formatting";

interface CreateFoodModalProps {
  handleCloseCreateFoodModal: () => void;
}

function CreateFoodModal({
  handleCloseCreateFoodModal,
}: CreateFoodModalProps) {
  const queryClient = useQueryClient();
  const { storageName } = useParams() as { storageName: string };
  const { inputList, setInputList, initialInputList, setIsSelected } =
    useFoodStore();
  const [foodData, setFoodData] = useState({
    name: "",
    categoryId: 0,
    price: 0,
    expiredDate: "",
    location: "",
    isManual: true,
  });

  useEffect(() => {
    setFoodData({
      name: inputList.foodName,
      categoryId: inputList.categoryId,
      price: inputList.price,
      expiredDate: formatDashDate(inputList.expiredDate),
      location: storageName,
      isManual: true,
    });
  }, [inputList]);

  // 식품 등록 api
  const { mutate: mutateFood } = useMutation({
    mutationFn: postFood,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["foodStorageItemList"],
      });
      setInputList(initialInputList);
      handleCloseCreateFoodModal();
      setIsSelected(false);
    },
  });

  const handleCreateFood = () => {
    mutateFood(foodData);
  };

  return (
    <div className={styles.wrapper}>
      <section className={styles.main_section}>
        <FoodSection option="create" />
      </section>
      <span>
        * 분류에 따른 <span>예상 소비기한</span>이 제공되나
        <br />
        상이할 수 있습니다.
      </span>
      <Button
        content="완료"
        color="red"
        onClick={handleCreateFood}
        disabled={foodData.name == "" || foodData.categoryId == 0}
      />
    </div>
  );
}

export default CreateFoodModal;
