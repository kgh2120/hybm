import useFridgeStore from "../../stores/useFridgeStore";
import styles from "../../styles/common/DesignItemBox.module.css";
import { useEffect, useState } from "react";

interface DesignItemBoxProps {
  name: string;
  content: string;
  option: string;
  imgSrc: string;
  isApplied: boolean;
  level: number;
  location: string;
  designId: number;
}

interface AppliedDesignIdListType {
  ice: number;
  cool: number;
  cabinet: number;
}
function DesignItemBox({
  name,
  content,
  option = "active",
  imgSrc,
  isApplied,
  level = 0,
  location,
  designId,
}: DesignItemBoxProps) {
  const [appliedDesignIdList, setAppliedDesignIdList] =
    useState<AppliedDesignIdListType>({
      ice: designId,
      cool: designId,
      cabinet: designId,
    });
  const { setAppliedDesign, appliedDesign } = useFridgeStore();

  useEffect(() => {
    if (isApplied) {
      console.log(`${location}의 ${designId}`)
      if (location === "찬장") {
        setAppliedDesignIdList({
          ...appliedDesignIdList,
          cabinet: designId,
        });
      } else if (location === "냉장칸") {
        setAppliedDesignIdList({
          ...appliedDesignIdList,
          cool: designId,
        });
      } else if (location === "냉동칸") {
        setAppliedDesignIdList({
          ...appliedDesignIdList,
          ice: designId,
        });
      }
    }
  }, []);

  const handleChangeAppliedDesign = () => {
    if (location === "찬장") {
      setAppliedDesign({
        ...appliedDesign,
        cabinet: { imgSrc, designId },
      });
      setAppliedDesignIdList({
        ...appliedDesignIdList,
        cabinet: designId,
      });
    } else if (location === "냉장칸") {
      setAppliedDesign({
        ...appliedDesign,
        cool: { imgSrc, designId },
      });
      setAppliedDesignIdList({
        ...appliedDesignIdList,
        cool: designId,
      });
    } else if (location === "냉동칸") {
      setAppliedDesign({
        ...appliedDesign,
        ice: { imgSrc, designId },
      });
      setAppliedDesignIdList({
        ...appliedDesignIdList,
        ice: designId,
      });
    }
  };
  console.log(appliedDesign.cabinet.designId, appliedDesignIdList.cabinet)
  console.log(appliedDesign.cool.designId, appliedDesignIdList.cool)
  console.log(appliedDesign.ice.designId, appliedDesignIdList.ice)
  return (
    <article className={styles.wrapper}>
      {option === "inactive" ? (
        <div className={styles.img_box}>
          <div className={styles.img_gray_box}></div>
          <img src={imgSrc} alt="상품아이콘" />
          <span>lv.{level}</span>
        </div>
      ) : appliedDesign.cabinet.designId ===
          appliedDesignIdList.cabinet ||
        appliedDesign.cool.designId === appliedDesignIdList.cool ||
        appliedDesign.ice.designId === appliedDesignIdList.ice ? (
        <div
          className={styles.img_box}
          onClick={handleChangeAppliedDesign}
        >
          <div className={styles.applied_img_box}></div>
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
