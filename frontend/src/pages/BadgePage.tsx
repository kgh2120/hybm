import MainPage from "./MainPage";
import styles from "../styles/designPage/DesignPage.module.css";
// import WhiteSection from "../components/common/WhiteSection";
import Button from "../components/common/Button";
import rightArrow from "../assets/images/rightArrow.png";
import { Link } from "react-router-dom";
import { getBadgeList } from "../api/badgeApi";
import { useQuery } from "@tanstack/react-query";

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

  console.log(badgeList)
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
        {/* <WhiteSection title="보유 뱃지" />
        <WhiteSection title="미보유 뱃지" /> */}
        <div className={styles.button_box}>
          <Button content="적용" color="red" onClick={() => {}} />
          <Link to="/">
            <Button
              content="홈으로"
              color="white"
              onClick={() => {}}
            />
          </Link>
        </div>
      </section>
      <Link to="/design">
        <img className={styles.left_arrow} src={rightArrow} alt="" />
      </Link>
    </div>
  );
}

export default BadgePage;
