import styles from "../../styles/mainPage/LevelUpModal.module.css";
import lock from "../../assets/images/lock.png";
import useFridgeStore from "../../stores/useFridgeStore";
import LevelUpItem from "./LevelUpItem";

function LevelUpModal({level}: { level: number | null}) {
  const { levelDesignList } = useFridgeStore();
  const acquiredDesign = levelDesignList.filter((design) => design.level === level)
  return (
    <div className={styles.wrapper}>
      <section>
        <span className={styles.content}>
          <span className={styles.level}>LV.{level}</span> 을
          달성했습니다.
          <br />
          다음 아이템을 획득했습니다.
        </span>
        <div className={styles.item_section}>
          <LevelUpItem content='배지슬롯 1개' imgSrc={lock}/>
          <LevelUpItem content={acquiredDesign[0].name} imgSrc={acquiredDesign[0].designImgSrc}/>
        </div>
      </section>
    </div>
  );
}

export default LevelUpModal;
