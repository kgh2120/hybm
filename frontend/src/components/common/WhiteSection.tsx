import styles from "../../styles/common/WhiteSection.module.css";
import clip from "../../assets/images/clip.png";
import DesignItemBox from "./DesignItemBox";

interface DesignType {
  designImgSrc: string;
  has: boolean;
  level: number;
  location: string;
  name: string;
  storageDesignId: number;
}

interface WhiteSectionProps {
  title: string;
  designList: DesignType[];
}

function WhiteSection({ title, designList }: WhiteSectionProps) {
  return (
    <section className={styles.wrapper}>
      <div>
        <h2>{title}</h2>
      </div>
      <img src={clip} alt="압정 이미지" />

      <section className={styles.main_section}>
        {designList.map((design: DesignType) => {
          return (
            <DesignItemBox
              key={design.storageDesignId}
              designId={design.storageDesignId}
              name={design.name}
              content=""
              option={design.has ? "active" : "inactive"}
              imgSrc={design.designImgSrc}
              level={design.level}
              location={design.location}
            />
          );
        })}
      </section>
    </section>
  );
}

export default WhiteSection;
