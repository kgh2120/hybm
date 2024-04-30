import styles from "../../styles/mainPage/LevelUpModal.module.css";

interface propsType {
    content: string;
    imgSrc: string;
}
function LevelUpItem({ content, imgSrc }: propsType) {
  return (
    <div className={styles.item}>
            <div className={styles.item_img_box}>
              <img className={styles.item_img} src={imgSrc} alt="" />
            </div>
            <span className={styles.item_text}>{content}</span>
          </div>
  )
}

export default LevelUpItem