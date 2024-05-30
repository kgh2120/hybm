import useFridgeStore from "../../stores/useFridgeStore";
import styles from "../../styles/common/DesignItemBox.module.css";

interface DesignItemBoxProps {
  name: string;
  content: string;
  option: string;
  imgSrc: string;
  level: number;
  location: string;
  designId: number;
}

function DesignItemBox({
  name,
  content,
  option = "active",
  imgSrc,
  level = 0,
  location,
  designId,
}: DesignItemBoxProps) {
  const { setAppliedDesign, appliedDesign } = useFridgeStore();

  const handleChangeAppliedDesign = () => {
    if (location === "찬장") {
      setAppliedDesign({
        ...appliedDesign,
        cabinet: { imgSrc, designId },
      });
    } else if (location === "냉장칸") {
      setAppliedDesign({
        ...appliedDesign,
        cool: { imgSrc, designId },
      });
    } else if (location === "냉동칸") {
      setAppliedDesign({
        ...appliedDesign,
        ice: { imgSrc, designId },
      });
    }
  };
  
  const isSelected = () => {
    return (
      appliedDesign.cabinet.designId === designId ||
      appliedDesign.cool.designId === designId ||
      appliedDesign.ice.designId === designId
    );
  };

  return (
    <article className={styles.wrapper}>
      {option === "inactive" ? (
        <div className={styles.img_box}>
          <div className={styles.img_gray_box} />
          <img src={imgSrc} alt="상품아이콘" />
          <span>lv.{level}</span>
        </div>
      ) : isSelected() ? (
        <div
          className={styles.img_box}
          onClick={handleChangeAppliedDesign}
        >
          <div className={styles.applied_img_box} />
          <img src={imgSrc} alt="상품아이콘" />
        </div>
      ) : (
        <div
          className={styles.img_box}
          onClick={handleChangeAppliedDesign}
        >
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

export default DesignItemBox;
