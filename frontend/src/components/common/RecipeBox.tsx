import styles from "../../styles/common/RecipeBox.module.css";
import food from "../../assets/extra-food.png"
import RightArrow from "../../assets/rightArrow.png";

function RecipeBox() {
  return (
    <section className={styles.wrapper}>
      <div className={styles.img_box}>
        <img src={food} alt="음식이미지" />
      </div>
      <div className={styles.text_box}>
        <h2>제목입니다 제목입니다 제목입니다 제목일것입니다 제목제목</h2>
        <button>
            레시피로 이동하기
            <img src={RightArrow} alt="버튼이미지" />
        </button>
      </div>
    </section>
  );
}

export default RecipeBox;
