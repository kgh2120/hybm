import addBtn from "../../assets/addBtn.png";
import styles from "../../styles/common/EmptyBox.module.css";

function EmptyBox() {
  return (
    <article className={styles.wrapper}>
      <div className={styles.img_box}>
        <img src={addBtn} alt="상품아이콘" />
      </div>
    </article>
  );
}

export default EmptyBox;
