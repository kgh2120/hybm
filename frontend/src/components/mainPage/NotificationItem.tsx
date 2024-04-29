import styles from "../../styles/mainPage/mainPage.module.css";
import meat from "../../assets/meat.png";

function NotificationItem() {
  return (
    <div className={styles.notification_item}>
      <div className={styles.image_section}>
        <img src={meat} alt="" />
      </div>
      <div className={styles.main_section}>
        <span>'냉장고'에서 '닭다리'가 3일 남았습니다.</span>
        <div>
          <span>2024.04.22</span>
          <div>
            <button>이미 먹었어요</button>
            <button>버렸어요</button>
          </div>
        </div>
      </div>
      <div className={styles.button_section}>x</div>
    </div>
  );
}

export default NotificationItem;
