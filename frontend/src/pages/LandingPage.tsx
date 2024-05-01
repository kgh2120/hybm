import styles from "../styles/landingPage/LandingPage.module.css";
import logo from "../assets/extra-logo.png";
import naverBtn from "../assets/naverBtn.png";
import kakaoBtn from "../assets/kakaoBtn.png";

function LandingPage() {
  const naverLogin = () => {
    window.location.href = `https://k10a707.p.ssafy.io/api/oauth2/authorization/naver?redirect_url=${
      import.meta.env.VITE_REDIRECT_URI_BASE
    }`
  }

  const kakaoLogin = () => {
    window.location.href = `https://k10a707.p.ssafy.io/api/oauth2/authorization/kakao?redirect_url=${
      import.meta.env.VITE_REDIRECT_URI_BASE
    }`
  }

  return (
    <div className={styles.wrapper}>
      <img src={logo} alt="log_err" />
      <div>
        <div className={styles.sub_header}>마트 다녀오셨어요?</div>
        <div>Have You Been To the Mart?</div>
      </div>
      <div className={styles.auth_btn}>
        <button type="button" onClick={naverLogin}>
          <img src={naverBtn} alt="naver_err" />
        </button>
        <button type="button" onClick={kakaoLogin}>
          <img src={kakaoBtn} alt="kakao_err" />
        </button>
      </div>
    </div>
  );
}

export default LandingPage;
