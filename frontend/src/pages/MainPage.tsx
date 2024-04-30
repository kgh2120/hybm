import { Link } from "react-router-dom";
import { useState } from "react";
import Modal from "../components/common/Modal";
import NotificationModal from "../components/mainPage/NotificationModal";
import styles from "../styles/mainPage/mainPage.module.css";
import LevelUpModal from "../components/mainPage/LevelUpModal";

function MainPage() {
  const [isNotificationModalOpen, setIsNotificationModalOpen] = useState(false);
  const [isLevelUpModalOpen, setIsLevelUpModalOpen] = useState(false);

  const handleOpenNotificationModal = () => {
    setIsNotificationModalOpen(true);
  };

  const handleCloseNotificationModal = () => {
    setIsNotificationModalOpen(false);
  };

  const handleOpenLevelUpModal = () => {
    setIsLevelUpModalOpen(true);
  };

  const handleCloseLevelUpModal = () => {
    setIsLevelUpModalOpen(false);
  };

  return (
    <div className={styles.wrapper}>
      <Link to="/badge">배지</Link>
      <Link to="/design">디자인</Link>
      <Link to="/landing">랜딩</Link>
      <Link to="/storage/cabinet">찬장</Link>
      <Link to="/storage/cool">냉장</Link>
      <Link to="/storage/ice">냉동</Link>
      <Link to="/report">보고서</Link>
      <Link to="/test">테스트</Link>
      <button onClick={handleOpenLevelUpModal}>레벨업</button>
      <button onClick={handleOpenNotificationModal}>알림</button>
      {isNotificationModalOpen && (
        <Modal title="알림함" clickEvent={handleCloseNotificationModal}>
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
