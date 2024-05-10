import styles from "../../styles/common/ItemBox.module.css";

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
  return (
    <article className={styles.wrapper} onClick={onClick}>
      {option === "inactive" ? (
        <div className={styles.img_box}>
          <div className={styles.img_gray_box}></div>
          <img src={imgSrc} alt="상품아이콘" />
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
        <span className={styles.item_name}>{name}</span>
        <span className={styles.item_content}>{content}</span>
      </div>
    </article>
  );
}

export default ItemBox;
