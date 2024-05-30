import emptyFridger from "../../assets/images/emptyFridger.png";
import styles from "../../styles/common/EmptySection.module.css";

interface EmptySectionType {
  content1: string;
  content2: string;
}

function EmptySection({ content1, content2 }: EmptySectionType) {
  return (
    <div className={styles.wrapper}>
      <div className={styles.header}>
        <img src={emptyFridger} alt="텅 이미지" />
        <h1>텅</h1>
      </div>
      <div className={styles.content_box}>
        <p className={styles.content1}>{content1}</p>
        <p className={styles.content2}>{content2}</p>
      </div>
    </div>
  );
}

export default EmptySection;

