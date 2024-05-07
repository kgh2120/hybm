import MainPage from "./MainPage";
import styles from "../styles/designPage/DesignPage.module.css";
import WhiteSection from "../components/common/WhiteSection";
import Button from "../components/common/Button";
import leftArrow from "../assets/images/leftArrow.png";
import { Link } from "react-router-dom";
import { getDesignList } from "../api/fridgeApi";
import { useQuery } from "@tanstack/react-query";

function DesignPage() {
  const {
    data: designList,
    isPending: isdesignListPending,
    isError: isdesignListError,
  } = useQuery({
    queryKey: ["designList"],
    queryFn: getDesignList,
  });

  if (isdesignListPending) {
    return <div>designList Loding...</div>;
  }
  if (isdesignListError) {
    return <div>designList error</div>;
  }
  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <MainPage />
      </div>
      <section className={styles.white_section}>
        <WhiteSection title="찬장" />
        <WhiteSection title="냉장" />
        <WhiteSection title="냉동" />
        <Button content="적용" color="red" />
      </section>
      <Link to="/badge">
        <img className={styles.left_arrow} src={leftArrow} alt="" />
      </Link>
    </div>
  );
}

export default DesignPage;
