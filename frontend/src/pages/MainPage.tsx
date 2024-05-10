import styles from "../styles/mainPage/MainPage.module.css";
import background from "../assets/images/background.png";
import recipe from "../assets/images/recipe.png";
import trashCan from "../assets/images/trashCan.png";
import { Link } from "react-router-dom";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import Modal from "../components/common/Modal";
import LevelUpModal from "../components/mainPage/LevelUpModal";
import ExpBar from "../components/common/ExpBar";
import { deleteAllFood, getBigCategoryList } from "../api/foodApi";
import { useFoodCategoryStore } from "../stores/useFoodStore";
import { getCurrentDesign } from "../api/fridgeApi";
import ConfirmModal from "../components/common/ConfirmModal";
import { getCurrentBadgeList } from "../api/badgeApi";
import useFridgeStore from "../stores/useFridgeStore";

interface StorageType {
  id: number;
  imgSrc: string;
}

interface CurrentDesignType {
  ice: StorageType;
  cool: StorageType;
  cabinet: StorageType;
}

function MainPage() {
  const { setBigCategoryList } = useFoodCategoryStore();
  const { appliedDesign, setAppliedDesign } = useFridgeStore();
  const [isLevelUpModalOpen, setIsLevelUpModalOpen] = useState(false);
  const [
    isDeleteAllFoodConfirmModalOpen,
    setIsDeleteAllFoodConfirmModalOpen,
  ] = useState(false);

  // const handleOpenLevelUpModal = () => {
  //   setIsLevelUpModalOpen(true);
  // };

  const handleCloseLevelUpModal = () => {
    setIsLevelUpModalOpen(false);
  };

  const {
    data: currentDesign,
    isPending: isCurrentDesignPending,
    isError: isCurrentDesignError,
  } = useQuery<CurrentDesignType>({
    queryKey: ["currentDesign"],
    queryFn: getCurrentDesign,
  });

  const {
    data: bigCategoryList,
    isPending: isBigCategoryListPending,
    isError: isBigCategoryListError,
  } = useQuery({
    queryKey: ["bigCategoryList"],
    queryFn: getBigCategoryList,
  });

  const {
    data: currentBadge,
    isPending: iscurrentBadgePending,
    isError: iscurrentBadgeError,
  } = useQuery({
    queryKey: ["currentBadge"],
    queryFn: getCurrentBadgeList,
  });

  console.log(currentBadge);
  const { mutate: mutateDeleteAllFood } = useMutation({
    mutationFn: deleteAllFood,
    onSuccess: () => {
      setIsDeleteAllFoodConfirmModalOpen(false);
    },
  });

  const handleDeleteAllFood = () => {
    mutateDeleteAllFood();
  };

  const closeDeleteAllFoodConfirmModal = () => {
    setIsDeleteAllFoodConfirmModalOpen(false);
  };

  const handleOpenDeleteAllFoodConfirmModal = () => {
    setIsDeleteAllFoodConfirmModalOpen(true);
  };

  useEffect(() => {
    if (bigCategoryList) {
      setBigCategoryList(bigCategoryList);
    }
  }, [bigCategoryList]);

  useEffect(() => {
    if (currentDesign) {
      setAppliedDesign({
        ice: {
          imgSrc: currentDesign.ice.imgSrc,
          designId: currentDesign.ice.id,
        },
        cool: {
          imgSrc: currentDesign.cool.imgSrc,
          designId: currentDesign.cool.id,
        },
        cabinet: {
          imgSrc: currentDesign.cabinet.imgSrc,
          designId: currentDesign.cabinet.id,
        },
      });
    }
  }, [currentDesign]);

  if (
    isBigCategoryListPending ||
    isCurrentDesignPending ||
    iscurrentBadgePending
  ) {
    return <div>MainPage Loding...</div>;
  }

  if (isBigCategoryListError) {
    return <div>bigCategoryList error</div>;
  } else if (isCurrentDesignError) {
    return <div>currentDesign error</div>;
  } else if (iscurrentBadgeError) {
    return <div>currentDesign error</div>;
  }

  return (
    <div className={styles.wrapper}>
      <ExpBar />
      <Link to="/storage/cabinet">
        <img
          className={styles.cabinet}
          src={appliedDesign.cabinet.imgSrc}
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
          src={appliedDesign.cool.imgSrc}
          alt="냉장이미지"
        />
      </Link>
      <Link to="/storage/ice">
        <img
          className={styles.ice}
          src={appliedDesign.ice.imgSrc}
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
        onClick={handleOpenDeleteAllFoodConfirmModal}
        src={trashCan}
        alt="쓰레기통 이미지"
      />

      {isLevelUpModalOpen && (
        <Modal title="레벨업!" clickEvent={handleCloseLevelUpModal}>
          <LevelUpModal />
        </Modal>
      )}
      {isDeleteAllFoodConfirmModalOpen && (
        <ConfirmModal
          content="모든 식품을 삭제하시겠습니까?"
          option1="삭제"
          option2="취소"
          option1Event={handleDeleteAllFood}
          option2Event={closeDeleteAllFoodConfirmModal}
        />
      )}
    </div>
  );
}

export default MainPage;
