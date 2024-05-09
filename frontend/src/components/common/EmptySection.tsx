import { ReactNode } from "react";
import emptyFridger from "../../assets/images/emptyFridger.png";
import styles from "../../styles/common/EmptySection.module.css";

interface Test {
  content1: string;
  content2: string;
}

function EmptySection({ content1, content2 }: Test) {
  return (
    <div className={styles.wrapper}>
      <div className={styles.header}>
        <img src={emptyFridger} alt="텅 이미지" />
        <h1>텅</h1>
      </div>
      <div className={styles.content}>
        <p className={styles.context1}>{content1}</p>
        <p className={styles.context2}>{content2}</p>
      </div>
    </div>
  );
}

export default EmptySection;
