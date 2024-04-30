import { Link } from "react-router-dom";
import { useState } from "react";
import Modal from "../components/common/Modal";
import NotificationModal from "../components/mainPage/NotificationModal";
import styles from "../styles/mainPage/mainPage.module.css";

function MainPage() {
  const [isNotificationModalOpen, setIsNotificationModalOpen] = useState(false);

  const handleOpenNotificationModal = () => {
    setIsNotificationModalOpen(true);
  };

  const handleCloseNotificationModal = () => {
    setIsNotificationModalOpen(false);
  };
  return (
    <div className={styles.wrapper}>
      {/* <Link to="/">
        <img src={levelBar} alt="" />
      </Link> */}
      <Link to="/badge">배지</Link>
      <Link to="/design">디자인</Link>
      <Link to="/landing">랜딩</Link>
      <Link to="/storage/cabinet">찬장</Link>
      <Link to="/storage/cool">냉장</Link>
      <Link to="/storage/ice">냉동</Link>
      <Link to="/report">보고서</Link>
      <button onClick={handleOpenNotificationModal}>알림</button>
      {isNotificationModalOpen && (
        <Modal title="알림함" clickEvent={handleCloseNotificationModal}>
          <NotificationModal />
        </Modal>
      )}
    </div>
  );
}

export default MainPage;
