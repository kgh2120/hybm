import useAttachedBadgeStore, {
  useBadgeStore,
} from "../../stores/useBadgeStore";
import styles from "../../styles/badgePage/BadgeItemBox.module.css";
import { addLineBreakBeforeNumber } from '../../utils/formatting';

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
  const {
    badgeId,
    name,
    badgeImgSrc,
    condition,
    isAttached,
    position,
  } = badge;

  const { attachedBadgeList, setAttachedBadgeList } =
    useAttachedBadgeStore();
  const { selectedBadge, setSelectedBadge } = useBadgeStore();
  const handleSelectBadge = () => {
    setSelectedBadge({ badgeId, src: badgeImgSrc, position });
  };
  const styleName = selectedBadge.badgeId === badgeId ? "attached_img_box" : "img_box";

  const formattedCondition = addLineBreakBeforeNumber(condition)

  return (
    <article className={styles.wrapper}>
      {option === "hasnot" ? (
        <div className={styles.img_box}>
          <div className={styles.img_gray_box}></div>
          <img src={badgeImgSrc} alt="상품아이콘" />
        </div>
      ) : (
        <div
          className={styles[styleName]}
          onClick={handleSelectBadge}
        >
          <img src={badgeImgSrc} alt="상품아이콘" />
        </div>
      )}
      <div className={styles.text_box}>
        <span className={styles.item_name}>{name}</span>
        <span className={styles.item_content} dangerouslySetInnerHTML={{ __html: formattedCondition }} />
      </div>
    </article>
  );
}

export default BadgeItemBox;
