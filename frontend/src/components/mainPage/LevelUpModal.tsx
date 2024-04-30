import styles from "../../styles/mainPage/LevelUpModal.module.css";
import meat from '../../assets/meat.png'
import lock from '../../assets/lock.png'
// import LevelUpItem from "./LevelUpItem";

function LevelUpModal() {
  return (
    <div className={styles.wrapper}>
      <section>
        <span className={styles.content}>
          <span className={styles.level}>LV.6</span> 을 달성했습니다.
          <br />
          다음 아이템을 획득했습니다.
        </span>
        <div className={styles.item_section}>
          <div className={styles.item}>
            <div className={styles.item_img_box}>
              <img className={styles.item_img} src={lock} alt="" />
            </div>
            <span className={styles.item_text}>배지슬롯 1개</span>
          </div>
          {/* <LevelUpItem content='배지슬롯 1개' imgSrc={}/> */}
          <div className={styles.item}>
            <div className={styles.item_img_box}>
              <img className={styles.item_img} src={meat} alt="" />
            </div>
            <span className={styles.item_text}>이글루 냉장실</span>
          </div>
        </div>
      </section>
    </div>
  );
}

export default LevelUpModal;
