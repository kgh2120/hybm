import styles from "../../styles/common/WhiteSection.module.css";
import clip from "../../assets/clip.png";
import ItemBox from "./ItemBox";

interface propsType {
  title: string;
}

function WhiteSection({ title }: propsType) {
  return (
    <section className={styles.wrapper}>
      <div>
        <h2>{title}</h2>
      </div>
      <img src={clip} alt="압정 이미지" />

      <section className={styles.main_section}>
        <ItemBox name="기본찬장" content="" option="active" />
        <ItemBox name="기본찬장" content="" option="active" />
        <ItemBox name="기본찬장" content="" option="active" />
        <ItemBox name="기본찬장" content="" option="active" />
        
      </section>
    </section>
  );
}

export default WhiteSection;
