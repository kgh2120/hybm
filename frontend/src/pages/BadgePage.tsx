import MainPage from "./MainPage";
import styles from "../styles/designPage/DesignPage.module.css";
import Button from "../components/common/Button";
import rightArrow from "../assets/images/rightArrow.png";
import { Link } from "react-router-dom";
import { getBadgeList } from "../api/badgeApi";
import { useQuery } from "@tanstack/react-query";
import BadgeWhiteSection from '../components/badgePage/BadgeWhiteSection';

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
  const {
    data: badgeList,
    isPending: isBadgeListPending,
    isError: isBadgeListError,
  } = useQuery<BadgeListType>({
    queryKey: ["badgeList"],
    queryFn: getBadgeList,
  });

  if (isBadgeListPending) {
    return <div>Loading...</div>;
  }
  if (isBadgeListError) {
    return <div>Error...</div>;
  }
  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <MainPage />
      </div>
      <section className={styles.white_section}>
        <BadgeWhiteSection title="보유 뱃지" badgeList={badgeList.has} option="has"/>
        <BadgeWhiteSection title="미보유 뱃지" badgeList={badgeList.hasnot} option="hasnot"/>
        <div className={styles.button_box}>
          <Button content="적용" color="red" onClick={() => {}} disabled={false}/>
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
