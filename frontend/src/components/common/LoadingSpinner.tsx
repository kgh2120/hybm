import styles from "../../styles/common/LoadingSpinner.module.css";
import loadingSpinner from "../../assets/images/loadingSpinner.gif";

function LoadingSpinner() {
  return (
    <div className={styles.wrapper}>
      <img src={loadingSpinner} alt="로딩스피너" />
      <span>잠시만 기다려주세요!</span>
    </div>
  );
}

export default LoadingSpinner;
