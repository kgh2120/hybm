import { useState } from "react";
import styles from "../../styles/common/ExpBar.module.css";
import barBackground from "../../assets/images/barBackground.png";
import barHeart from "../../assets/images/barHeart.png";
import expBar from "../../assets/images/expBar.png";
import notification from "../../assets/images/notification.png";
import Modal from "./Modal";
import NotificationModal from "../mainPage/NotificationModal";
import { getLevelAndExp } from "../../api/userApi";
import { useQuery } from "@tanstack/react-query";
import { getIsNewNotification } from '../../api/notificationApi';

interface IsNewNotificationType {
  hasNew: boolean;
}
function ExpBar() {
  const [isNotificationModalOpen, setIsNotificationModalOpen] =
    useState(false);

  const handleOpenNotificationModal = () => {
    setIsNotificationModalOpen(true);
  };

  const handleCloseNotificationModal = () => {
    setIsNotificationModalOpen(false);
  };

  const {
    data: levelAndExp,
    isPending: isLevelAndExpPending,
    isError: isLevelAndExpError,
  } = useQuery({
    queryKey: ["levelAndExp"],
    queryFn: getLevelAndExp,
  });

  const {
    data: isNewNotification,
    isPending: isNewNotificationPending,
    isError: isNewNotificationError,
  } = useQuery<IsNewNotificationType>({
    queryKey: ["isNewNotification"],
    queryFn: getIsNewNotification,
  });

  if (isLevelAndExpPending || isNewNotificationPending) {
    return <div>levelBar Loding...</div>;
  }
  if (isLevelAndExpError) {
    return <div>get level and exp error</div>;
  } else if (isNewNotificationError) {
    return <div>get isNewNotification error</div>;
  }

  const currentExpPercent =
    Math.round((levelAndExp.currentExp / levelAndExp.maxExp) * 100);

  return (
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
            <span>{levelAndExp.level}</span>
          </div>
        </div>

        <div className={styles.exp_bar_box}>
          <div className={styles.exp_bar_sub_box}>
            <img
              className={styles.exp_bar}
              src={expBar}
              alt="경험치바 이미지"
            />
            <div
              className={styles.current_exp}
              style={{ width: `${currentExpPercent}%` }}
            ></div>
            <span className={styles.exp_content}>
              {levelAndExp.currentExp} / {levelAndExp.maxExp} (
              {currentExpPercent}%)
            </span>
          </div>
        </div>
        <div
          className={styles.notification_box}
          onClick={handleOpenNotificationModal}
        >
          <div className={styles.notification_sub_box}>
            <img src={notification} alt="알림 이미지" />
            {isNewNotification.hasNew && <div></div>}
          </div>
        </div>
      </div>
      {isNotificationModalOpen && (
        <Modal
          title="알림함"
          clickEvent={handleCloseNotificationModal}
        >
          <NotificationModal isNewNotification={isNewNotification.hasNew}/>
        </Modal>
      )}
    </div>
  );
}

export default ExpBar;
