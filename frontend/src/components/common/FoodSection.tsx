// import { ChangeEvent } from "react";
import styles from "../../styles/common/FoodSection.module.css";
// import { formatPrice } from "../../utils/formatting";
import ExpiryDateSelector from "./ExpiryDateSelector";
import meat from "../../assets/meat.png";
import search from "../../assets/search.png";

// interface propsType {
//   name: string | number | boolean | ((e: React.ChangeEvent<HTMLInputElement>) => void)
//   changeName: string | number | boolean | ((e: React.ChangeEvent<HTMLInputElement>) => void)
//   price: string | number | boolean | ((e: React.ChangeEvent<HTMLInputElement>) => void)
//   setPrice: React.Dispatch<React.SetStateAction<number | string>>;
// }

function FoodSection() {
  
  // const handleChangePrice = (e: ChangeEvent<HTMLInputElement>) => {
  //   const updatedPrice = e.target.value.replace(/[^0-9]/g, ""); // 숫자 이외의 문자 제거
  //   setPrice(updatedPrice);
  // };

  return (
    <div className={styles.wrapper}>
      <article className={styles.food_option_box}>
        <span>상품명</span>
        <input type="text" />
      </article>
      <article className={styles.food_option_box}>
        <span>분류</span>
        <div className={styles.category_box}>
          <img className={styles.category_img} src={meat} alt="" />
          <input
            type="text"
            // value={formatPrice(price)}
            // onChange={handleChangePrice}
          />
          <img
            className={styles.category_search_img}
            src={search}
            alt=""
          />
        </div>
      </article>
      <article className={styles.food_option_box}>
        <span>소비기한</span>
        <ExpiryDateSelector />
      </article>
      <article className={styles.food_option_box}>
        <span>가격</span>
        <div className={styles.price_box}>
          <input
            type="text"
            // value={formatPrice(price)}
            // onChange={handleChangePrice}
          />
          <span>원</span>
        </div>
      </article>
      <article className={styles.food_option_box}>
        <span>위치</span>
        <select>
          <option value="냉동실">냉동실</option>
          <option value="냉장실">냉장실</option>
          <option value="찬장">찬장</option>
        </select>
      </article>
    </div>
  );
}

export default FoodSection;
