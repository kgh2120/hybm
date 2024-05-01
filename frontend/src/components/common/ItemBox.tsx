import meat from "../../assets/meat.png";
import styles from "../../styles/common/ItemBox.module.css";

interface propsType {
  option: string;
}
function ItemBox({ option = "active" }: propsType) {
  return (
    <article className={styles.wrapper}>
      <div className={styles.img_box}>
        
        {option === "inactive" ? <div className={styles.img_gray_box}><img src={meat} alt="" /></div> : <img src={meat} alt="" />}
      </div>
      <div className={styles.text_box}>
        <span className={styles.item_name}>고기고기</span>
        <span className={styles.item_content}>D-7</span>
      </div>
    </article>
  );
}

export default ItemBox;
