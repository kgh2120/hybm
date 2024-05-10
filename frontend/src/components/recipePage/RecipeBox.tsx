import styles from "../../styles/common/RecipeBox.module.css";
import rightArrow from "../../assets/images/rightArrow.png";

interface RecipeBoxProps {
  imgSrc: string;
  title: string;
  onClick: () => void;
}

function RecipeBox({ imgSrc, title, onClick }: RecipeBoxProps) {
  return (
    <section className={styles.wrapper}>
      <div className={styles.img_box}>
        <img src={imgSrc} alt="음식이미지" />
      </div>
      <div className={styles.text_box}>
        <h2>{title}</h2>
        <button onClick={onClick}>
          레시피로 이동하기
          <img src={rightArrow} alt="버튼이미지" />
        </button>
      </div>
    </section>
  );
}

export default RecipeBox;
