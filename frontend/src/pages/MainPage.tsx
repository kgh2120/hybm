import styles from "../styles/testPage/TestPage.module.css";
import modernIce from "../assets/modernIce.png";
import modernCool from "../assets/modernCool.png";
import modernCabinet from "../assets/modernCabinet.png";
import background from "../assets/background.png";
import recipe from "../assets/recipe.png";
import barBackground from "../assets/barBackground.png";
import barHeart from "../assets/barHeart.png";
import expBar from "../assets/expBar.png";
import trashCan from "../assets/trashCan.png";
import notification from "../assets/notification.png";
import { Link } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getFoodCategoryList } from "../api/foodApi";
import { useState } from "react";
import Modal from "../components/common/Modal";
import NotificationModal from "../components/mainPage/NotificationModal";
import LevelUpModal from "../components/mainPage/LevelUpModal";

function MainPage() {
  const [isNotificationModalOpen, setIsNotificationModalOpen] =
    useState(false);
  const [isLevelUpModalOpen, setIsLevelUpModalOpen] = useState(false);

  const handleOpenNotificationModal = () => {
    setIsNotificationModalOpen(true);
  };

  const handleCloseNotificationModal = () => {
    setIsNotificationModalOpen(false);
  };

  // const handleOpenLevelUpModal = () => {
  //   setIsLevelUpModalOpen(true);
  // };

  const handleCloseLevelUpModal = () => {
    setIsLevelUpModalOpen(false);
  };

  const {
    data: foodCategoryList,
    isPending: isFoodCategoryListPending,
    isError: isFoodCategoryListError,
  } = useQuery({
    queryKey: ["foodCategoryList"],
    queryFn: getFoodCategoryList,
  });

  if (isFoodCategoryListPending) {
    return <div>isLoding...</div>;
  }
  if (isFoodCategoryListError) {
    return <div>error</div>;
  }

  // console.log(foodCategoryList);
  return (
    <div className={styles.wrapper}>
      <div className={styles.bar_box}>
        <div className={styles.bar_sub_box}>
          <img
            className={styles.bar_background}
            src={barBackground}
            alt="경험치바 배경이미지"
          />
          <div className={styles.bar_heart_box}>
            <div className={styles.bar_heart_sub_box}>
              <img
                className={styles.bar_heart}
                src={barHeart}
                alt="하트이미지"
              />
              <span>2</span>
            </div>
          </div>

          <img
            className={styles.exp_bar}
            src={expBar}
            alt="경험치바 이미지"
          />
          <div className={styles.current_exp}></div>
          <div className={styles.notification_box} onClick={handleOpenNotificationModal}>
            <div className={styles.notification_sub_box}>
              <img src={notification} alt="알림 이미지" />
              <div></div>
            </div>
          </div>
        </div>
      </div>
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
        src={trashCan}
        alt="쓰레기통 이미지"
      />
      {isNotificationModalOpen && (
        <Modal
          title="알림함"
          clickEvent={handleCloseNotificationModal}
        >
          <NotificationModal />
        </Modal>
      )}
      {isLevelUpModalOpen && (
        <Modal title="레벨업!" clickEvent={handleCloseLevelUpModal}>
          <LevelUpModal />
        </Modal>
      )}
    </div>
  );
}

export default MainPage;
