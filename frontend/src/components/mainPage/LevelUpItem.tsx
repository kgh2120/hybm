import styles from "../../styles/mainPage/LevelUpModal.module.css";

interface LevelUpItemProps {
  content: string;
  imgSrc: string;
}
function LevelUpItem({ content, imgSrc }: LevelUpItemProps) {
  return (
    <div className={styles.item}>
      <div className={styles.item_img_box}>
        <img className={styles.item_img} src={imgSrc} alt="아이템 이미지" />
      </div>
      <span className={styles.item_text}>{content}</span>
    </div>
  );
}

export default LevelUpItem;
