import useAttachedBadgeStore from '../../stores/useBadgeStore';
import styles from "../../styles/badgePage/BadgeItemBox.module.css";

interface BadgeType {
  badgeId: number;
  name: string;
  badgeImgSrc: string;
  condition: string;
  isAttached: boolean;
  position: number;
}

interface BadgeItemBoxProps {
  badge: BadgeType;
  option: string;
}

function BadgeItemBox({ badge, option }: BadgeItemBoxProps) {
  const { badgeId, name, badgeImgSrc, condition, isAttached, position } = badge;

  const { attachedBadgeList, setAttachedBadgeList } = useAttachedBadgeStore();


  const onDragStart = (e: React.DragEvent<HTMLDivElement>, badgeId: number) => {
    e.dataTransfer.effectAllowed = 'move';
    e.dataTransfer.setData('selectedBadge', String(badgeId));
  };

  const onDragDrop = (e: React.DragEvent) => {
    e.preventDefault();
    console.log('gd')
    
    // setAttachedBadgeList(updateImages);
  };

  return (
    <article className={styles.wrapper} >
      {option === "hasnot" ? (
        <div className={styles.img_box}>
          <div className={styles.img_gray_box}></div>
          <img src={badgeImgSrc} alt="상품아이콘" />
        </div>
      ) : (
        <div className={styles.img_box} draggable onDragStart={(e) => onDragStart(e, badgeId)} onDragEnd={(e) => onDragDrop(e)}>
          <img src={badgeImgSrc} alt="상품아이콘" />
        </div>
      )}
      <div className={styles.text_box}>
        <span className={styles.item_name}>{name}</span>
        <span className={styles.item_content}>{condition}</span>
      </div>
    </article>
  );
}

export default BadgeItemBox;
