import Button from "../common/Button";
import styles from "../../styles/storagePage/CreateFoodModal.module.css";
import barcode from "../../assets/barcode.png";
import camera from "../../assets/camera.png";
// import { useInput } from "../../hooks/useInput";
import FoodSection from "../common/FoodSection";

function CreateFoodModal() {
  // const [category, changeCategory] = useInput("닭고기");
  return (
    <div className={styles.wrapper}>
      <section className={styles.main_section}>

        <FoodSection />
        <section className={styles.btn_section}>
          <div><img src={barcode} alt="바코드아이콘" /></div>
          <div><img src={camera} alt="카메라아이콘" /></div>
          <div></div>
        </section>
      </section>
      {/* <section className={styles.main_section}>
        <div className={styles.img_section}>
          <div className={styles.img_article}>
            <div className={styles.food_option_box}>
              <span>상품명</span>
              <input placeholder="바코드를 촬영하거나 직접 입력해보세요."></input>
            </div>
            <div className={styles.food_option_box}>
              <span>분류</span>
              <div className={styles.category_box}>
                <img src={meat} alt="" />
                <input
                  value={category}
                  onChange={changeCategory}
                  type="text"
                  name=""
                  id=""
                ></input>
              </div>
            </div>
          </div>
          <img className={styles.button} src={barcode} alt="" />
        </div>
        <div className={styles.img_section}>
          <div className={styles.food_option_box}>
            <span>소비기한</span>
            <ExpiryDateSelector />
          </div>
          <img className={styles.button} src={camera} alt="" />
        </div>
        <div className={styles.food_option_box}>
          <span>가격</span>
          <input></input>
        </div>
        <div className={styles.food_option_box}>
          <span>위치</span>
          <input></input>
        </div>
      </section> */}
      <Button content="완료" color="red" />
    </div>
  );
}

export default CreateFoodModal;
