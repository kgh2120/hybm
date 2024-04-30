import styles from "../../styles/mainPage/NotificationModal.module.css";
import NotificationItem from "./NotificationItem";

function NotificationModal() {
  return (
    <div className={styles.notification_modal_wrapper}>
      <button className={styles.remove_all_button}>알림함 비우기</button>
      <NotificationItem />
      <NotificationItem />
      <NotificationItem />
      <NotificationItem />
      <NotificationItem />
    </div>
  );
}

export default NotificationModal;
