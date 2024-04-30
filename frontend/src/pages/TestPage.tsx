import styles from "../styles/testPage/TestPage.module.css";
import modernIce from "../assets/modernIce.png";
import modernCool from "../assets/modernCool.png";
import modernCabinet from "../assets/modernCabinet.png";
import { Link } from "react-router-dom";
function TestPage() {
  return (
    <div className={styles.wrapper}>
      {/* <div className={styles.set}> */}
      <img className={styles.cabinet} src={modernCabinet} alt="" />
      <Link to="/storage/cabinet">
        <button className={styles.cabinet_btn}>gd</button>
      </Link>
      <button className={styles.cool_btn}>냉장</button>
      <button className={styles.ice_btn}>냉동</button>
      <img className={styles.cool} src={modernCool} alt="" />
      <img className={styles.ice} src={modernIce} alt="" />
      {/* </div> */}
    </div>
  );
}

export default TestPage;
