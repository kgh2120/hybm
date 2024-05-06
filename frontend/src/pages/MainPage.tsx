import styles from "../styles/mainPage/MainPage.module.css";
import modernIce from "../assets/modernIce.png";
import modernCool from "../assets/modernCool.png";
import modernCabinet from "../assets/modernCabinet.png";
import background from "../assets/background.png";
import recipe from "../assets/recipe.png";
import trashCan from "../assets/trashCan.png";
import { Link } from "react-router-dom";
import { useMutation, useQuery } from "@tanstack/react-query";
// import { getFoodCategoryList } from "../api/foodApi";
import { useEffect, useState } from "react";
import Modal from "../components/common/Modal";
import LevelUpModal from "../components/mainPage/LevelUpModal";
import ExpBar from '../components/common/ExpBar';
import { getIsNewNotification } from '../api/notificationApi';
import { deleteAllFood, getBigCategoryList } from '../api/foodApi';
import useFoodStore from '../stores/useFoodStore';
import { getCurrentDesign } from '../api/fridgeApi';
import ConfirmModal from '../components/common/ConfirmModal';

function MainPage() {
  
  const { setBigCategoryList } = useFoodStore();
  const [isLevelUpModalOpen, setIsLevelUpModalOpen] = useState(false);
  const [isDeleteAllFoodConfirmModalOpen, setIsDeleteAllFoodConfirmModalOpen] = useState(false);
  
  // const handleOpenLevelUpModal = () => {
  //   setIsLevelUpModalOpen(true);
  // };

  const handleCloseLevelUpModal = () => {
    setIsLevelUpModalOpen(false);
  };

  // const {
  //   data: foodCategoryList,
  //   isPending: isFoodCategoryListPending,
  //   isError: isFoodCategoryListError,
  // } = useQuery({
  //   queryKey: ["foodCategoryList"],
  //   queryFn: getFoodCategoryList,
  // });

  // if (isFoodCategoryListPending) {
  //   return <div>isLoding...</div>;
  // }
  // if (isFoodCategoryListError) {
  //   return <div>error</div>;
  // }

  // console.log(foodCategoryList);

  // const { data: isNewNotification, isPending: isNewNotificationPending, isError: isNewNotificationError } = useQuery({
  //   queryKey: ["isNewNotification"],
  //   queryFn: getIsNewNotification,
  // })

  // if (isNewNotificationPending) {
  //   return <div>isNewNofication Loding...</div>;
  // }
  // if (isNewNotificationError) {
  //   return <div>isNewNofication error</div>;
  // }
  

  // const { data: currentDesign, isPending: isCurrentDesignPending, isError: isCurrentDesignError } = useQuery({
  //   queryKey: ["currentDesign"],
  //   queryFn: getCurrentDesign,
  // })

  const { data: bigCategoryList, isPending: isBigCategoryListPending, isError: isBigCategoryListError } = useQuery({
    queryKey: ["isNewNotification"],
    queryFn: getBigCategoryList,
  })

  const { mutate: mutateDeleteAllFood } = useMutation({
    mutationFn: deleteAllFood,
    onSuccess: () => {
      setIsDeleteAllFoodConfirmModalOpen(false)
    }
  })
  
  const handleDeleteAllFood = () => {
    mutateDeleteAllFood()
  }

  const closeDeleteAllFoodConfirmModal = () => {
    setIsDeleteAllFoodConfirmModalOpen(false)
  }
  useEffect(() => {
    if (bigCategoryList) {
      setBigCategoryList(bigCategoryList);
    }
  }, [bigCategoryList])

  if (isBigCategoryListPending) {
    return <div>MainPage Loding...</div>;
  } 

  if (isBigCategoryListError) {
    return <div>bigCategoryList error</div>;
  } 
  // else if (isCurrentDesignError) {
  //   return <div>currentDesign error</div>;
  // }

  return (
    <div className={styles.wrapper}>
      <ExpBar />
      <Link to="/storage/cabinet">
        <img
          className={styles.cabinet}
          src={modernCabinet}
          alt="찬장이미지"
        />
      </Link>
      <Link to="/recipe">
        <img
          className={styles.recipe}
          src={recipe}
          alt="레시피이미지"
        />
      </Link>
      <Link to="/storage/cool">
        <img
          className={styles.cool}
          src={modernCool}
          alt="냉장이미지"
        />
      </Link>
      <Link to="/storage/ice">
        <img
          className={styles.ice}
          src={modernIce}
          alt="냉동이미지"
        />
      </Link>
      <img
        className={styles.background}
        src={background}
        alt="배경이미지"
      />
      <Link to="/receipt">
        <div className={styles.receipt}>영수증</div>
      </Link>
      <Link to="/design">
        <div className={styles.design}>디자인</div>
      </Link>
      <Link to="/report">
        <div className={styles.report}>보고서</div>
      </Link>
      <img
        className={styles.trash_can}
        onClick={handleDeleteAllFood}
        src={trashCan}
        alt="쓰레기통 이미지"
      />
      
      {isLevelUpModalOpen && (
        <Modal title="레벨업!" clickEvent={handleCloseLevelUpModal}>
          <LevelUpModal />
        </Modal>
      )}
      {isDeleteAllFoodConfirmModalOpen && 
        <ConfirmModal content="모든 식품을 삭제하시겠습니까?" option1="삭제" option2="취소" option1Event={handleDeleteAllFood} option2Event={closeDeleteAllFoodConfirmModal} />
      }
    </div>
  );
}

export default MainPage;
