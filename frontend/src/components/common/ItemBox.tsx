import styles from "../../styles/common/ItemBox.module.css";
import check from "../../assets/images/check.png";
import { useLocation } from "react-router-dom";

interface ItemBoxProps {
  name: string;
  content: string;
  option: string;
  imgSrc: string;
  onClick: () => void;
}
function ItemBox({
  name,
  content,
  option = "active",
  imgSrc,
  onClick,
}: ItemBoxProps) {
  const location = useLocation();
  const nameLength = location.pathname === "/report" ? 3 : 5
  const fomattedName =
    name.length > nameLength ? `${name.slice(0, nameLength)}..` : name;

  return (
    <article className={styles.wrapper} onClick={onClick}>
      {option === "inactive" ? (
        <div className={styles.img_box}>
          <div className={styles.img_gray_box}></div>
          <img src={imgSrc} alt="상품아이콘" />
        </div>
      ) : option === "clicked" ? (
        <div className={styles.clicked_img_box}>
          <div className={styles.img_solid_white_box}></div>
          <img
            className={styles.food_img}
            src={imgSrc}
            alt="상품아이콘"
          />
          <img
            className={styles.check_img}
            src={check}
            alt="체크아이콘"
          />
        </div>
      ) : option === "report" ? (
        <div className={styles.report_img_box}>
          <div className={styles.img_white_box}>
            <img src={imgSrc} alt="상품아이콘" />
          </div>
        </div>
      ) : (
        <div className={styles.img_box}>
          <img src={imgSrc} alt="상품아이콘" />
        </div>
      )}
      <div className={styles.text_box}>
        <span className={styles.item_name}>{fomattedName}</span>
        <span className={styles.item_content}>{content}</span>
      </div>
    </article>
  );
}

export default ItemBox;
