import styles from "../../styles/badgePage/BadgeWhiteSection.module.css";
import clip from "../../assets/images/clip.png";
import BadgeItemBox from "./BadgeItemBox";
import useAttachedBadgeStore from "../../stores/useBadgeStore";

interface BadgeType {
  badgeId: number;
  name: string;
  badgeImgSrc: string;
  condition: string;
  isAttached: boolean;
  position: number;
}

interface BadgeWhiteSectionProps {
  title: string;
  badgeList: BadgeType[];
  option: string;
}

function BadgeWhiteSection({
  title,
  badgeList,
  option,
}: BadgeWhiteSectionProps) {
  const { attachedBadgeList } = useAttachedBadgeStore();
  return (
    <section className={styles.wrapper}>
      <h2>{title}</h2>
      <img src={clip} alt="압정 이미지" />
      <section className={styles.main_section}>
        {badgeList.map((badge: BadgeType) => {
          if (
            !attachedBadgeList.some(
              (attachedBadge) =>
                attachedBadge.badgeId === badge.badgeId &&
                attachedBadge.position !== null
            )
          ) {
            return (
              <BadgeItemBox
                key={badge.badgeId}
                badge={badge}
                option={option}
              />
            );
          }
        })}
      </section>
    </section>
  );
}

export default BadgeWhiteSection;
