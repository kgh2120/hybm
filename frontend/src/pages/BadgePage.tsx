import MainPage from "./MainPage";
import styles from "../styles/designPage/DesignPage.module.css";
import WhiteSection from "../components/common/WhiteSection";
import Button from "../components/common/Button";
import rightArrow from "../assets/images/rightArrow.png";
import { Link } from "react-router-dom";

function BadgePage() {
  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <MainPage />
      </div>
      <section className={styles.white_section}>
        <WhiteSection title="보유 뱃지" />
        <WhiteSection title="미보유 뱃지" />
        <Button content="적용" color="red" />
      </section>
      <Link to="/design">
        <img className={styles.left_arrow} src={rightArrow} alt="" />
      </Link>
    </div>
  );
}

export default BadgePage;
