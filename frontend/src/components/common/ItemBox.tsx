import meat from "../../assets/meat.png";
import styles from "../../styles/common/ItemBox.module.css";

interface propsType {
  name: string;
  content: string;
  option: string;
}
function ItemBox({ name, content, option = "active" }: propsType) {
  return (
    <article className={styles.wrapper}>
      {option === "inactive" ? (
        <div className={styles.img_box}>
          <div className={styles.img_gray_box}>
            <img src={meat} alt="상품아이콘" />
          </div>
        </div>
      ) : option === "report" ? (
        <div className={styles.report_img_box}>
          <div className={styles.img_white_box}>
            <img src={meat} alt="상품아이콘" />
          </div>
        </div>
      ) : (
        <div className={styles.img_box}>
          <img src={meat} alt="상품아이콘" />
        </div>
      )}
      <div className={styles.text_box}>
        <span className={styles.item_name}>{name}</span>
        <span className={styles.item_content}>{content}</span>
      </div>
    </article>
  );
}

export default ItemBox;
