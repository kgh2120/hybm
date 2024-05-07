import styles from "../styles/landingPage/LandingPage.module.css";
import logo from "../assets/logo.png";
import naverBtn from "../assets/naver.png";
import kakaoBtn from "../assets/kakao.png";
import CustomButton from "../components/landingPage/CustomButton";

function LandingPage() {
  const naverLogin = () => {
    window.location.href = `https://k10a707.p.ssafy.io/api/oauth2/authorization/naver?redirect_url=${
      import.meta.env.VITE_REDIRECT_URI_BASE
    }`;
  };

  const kakaoLogin = () => {
    window.location.href = `https://k10a707.p.ssafy.io/api/oauth2/authorization/kakao?redirect_url=${
      import.meta.env.VITE_REDIRECT_URI_BASE
    }`;
  };

  return (
    <div className={styles.wrapper}>
      <img src={logo} alt="서비스 로고" />
      <div>
        <div className={styles.sub_header}>마트 다녀오셨어요?</div>
        <div>Have You Been To the Mart?</div>
      </div>
      <div className={styles.auth_btn}>
        <CustomButton option="naver" onClick={naverLogin}>
          <img src={naverBtn} alt="네이버 버튼" />
          <span>네이버로 시작하기</span>
        </CustomButton>
        <CustomButton option="kakao" onClick={kakaoLogin}>
          <img src={kakaoBtn} alt="카카오 버튼" />
          <span>카카오로 시작하기</span>
        </CustomButton>
      </div>
    </div>
  );
}

export default LandingPage;
