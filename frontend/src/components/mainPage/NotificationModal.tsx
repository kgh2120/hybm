import styles from "../../styles/mainPage/mainPage.module.css";
import NotificationItem from "./NotificationItem";

function NotificationModal() {
  return (
    <div className={styles.notification_modal_wrapper}>
      <NotificationItem />
      <NotificationItem />
    </div>
  );
}

export default NotificationModal;
