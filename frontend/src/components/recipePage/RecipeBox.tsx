import styles from "../../styles/common/RecipeBox.module.css";
import rightArrow from "../../assets/images/rightArrow.png";

interface RecipeBoxProps {
  title: string;
  author: string;
  onClick: () => void;
}

function RecipeBox({ author, title, onClick }: RecipeBoxProps) {
  return (
    <section className={styles.wrapper}>
      <div className={styles.text_box}>
        <h2>{title}</h2>
        <p>글쓴이: {author}</p>
      </div>
      <button onClick={onClick}>
        레시피로
        <br />
        이동하기
        <img src={rightArrow} alt="버튼이미지" />
      </button>
    </section>
  );
}

export default RecipeBox;
