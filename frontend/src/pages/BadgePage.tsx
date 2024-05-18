import MainPage from "./MainPage";
import styles from "../styles/designPage/DesignPage.module.css";
import Button from "../components/common/Button";
import rightArrow from "../assets/images/rightArrow.png";
import { Link, useNavigate } from "react-router-dom";
import { getBadgeList, putBadgePosition } from "../api/badgeApi";
import { useMutation, useQuery } from "@tanstack/react-query";
import BadgeWhiteSection from '../components/badgePage/BadgeWhiteSection';
import useAttachedBadgeStore from '../stores/useBadgeStore';
import LoadingSpinner from "../components/common/LoadingSpinner";

interface BadgeType {
  badgeId: number;
  name: string;
  badgeImgSrc: string;
  condition: string;
  isAttached: boolean;
  position: number;
}

interface BadgeListType {
  has: BadgeType[];
  hasnot: BadgeType[];
}

function BadgePage() {
  const { attachedBadgeList } = useAttachedBadgeStore();
  const navigate = useNavigate();
  const {
    data: badgeList,
    isPending: isBadgeListPending,
    isError: isBadgeListError,
  } = useQuery<BadgeListType>({
    queryKey: ["badgeList"],
    queryFn: getBadgeList,
  });

  const { mutate: mutatePutBadgePosition } = useMutation({
    mutationFn: putBadgePosition,
    onSuccess: () => {
      navigate("/")
    }
  });
  
  const handlePutBadgePosition = () => {
    const badgeListParams = attachedBadgeList.map((badge) => ({
      badgeId: badge.badgeId, position: badge.position
    }))
    mutatePutBadgePosition(badgeListParams)
  }

  if (isBadgeListPending) {
    return <LoadingSpinner />;
  }
  if (isBadgeListError) {
    return <div>Error...</div>;
  }
  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <MainPage />
      </div>
      <h2>배지 붙이기</h2>
      <section className={styles.white_section}>
        <BadgeWhiteSection title="보유 뱃지" badgeList={badgeList.has} option="has"/>
        <BadgeWhiteSection title="미보유 뱃지" badgeList={badgeList.hasnot} option="hasnot"/>
        <div className={styles.button_box}>
          <Button content="적용" color="red" onClick={handlePutBadgePosition} disabled={false}/>
          <Link to="/">
            <Button
              content="홈으로"
              color="white"
              onClick={() => {}}
              disabled={false}
            />
          </Link>
        </div>
      </section>
      <Link to="/design">
        <img className={styles.left_arrow} src={rightArrow} alt="오른쪽화살표 이미지" />
      </Link>
    </div>
  );
}

export default BadgePage;
