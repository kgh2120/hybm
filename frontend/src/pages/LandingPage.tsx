import styles from "../styles/landingPage/LandingPage.module.css";
import logo from "../assets/extra-logo.png";
import naverBtn from "../assets/naverBtn.png";
import kakaoBtn from "../assets/kakaoBtn.png";

function LandingPage() {
  return (
    <div className={styles.wrapper}>
      <img src={logo} alt="log_err" />
      <div>
        <div className={styles.sub_header}>마트 다녀오셨어요?</div>
        <div>Have You Been To the Mart?</div>
      </div>
      <div className={styles.auth_btn}>
        <img src={naverBtn} alt="naver_err" />
        <img src={kakaoBtn} alt="kakao_err" />
      </div>
    </div>
  );
}

export default LandingPage;
