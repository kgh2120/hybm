// import { ChangeEvent } from "react";
import { useState } from 'react';
import styles from "../../styles/common/FoodSection.module.css";
import CategoryBox from './CategoryBox';
// import { formatPrice } from "../../utils/formatting";
import ExpiryDateSelector from "./ExpiryDateSelector";
import { stringToDate } from '../../utils/formatting';


// interface propsType {
//   name: string | number | boolean | ((e: React.ChangeEvent<HTMLInputElement>) => void)
//   changeName: string | number | boolean | ((e: React.ChangeEvent<HTMLInputElement>) => void)
//   price: string | number | boolean | ((e: React.ChangeEvent<HTMLInputElement>) => void)
//   setPrice: React.Dispatch<React.SetStateAction<number | string>>;
// }

function FoodSection() {
  const [expiredDate, setExpiredDate] = useState<string>("");
  // const handleChangePrice = (e: ChangeEvent<HTMLInputElement>) => {
  //   const updatedPrice = e.target.value.replace(/[^0-9]/g, ""); // 숫자 이외의 문자 제거
  //   setPrice(updatedPrice);
  // };

  const { year, month, day } = stringToDate(expiredDate);

  return (
    <div className={styles.wrapper}>
      <article className={styles.food_option_box}>
        <span>상품명</span>
        <input type="text" />
      </article>
      <article className={styles.food_option_box}>
        <span>분류</span>
        <CategoryBox />
      </article>
      <article className={styles.food_option_box}>
        <span>소비기한</span>
        <ExpiryDateSelector year={year} month={month} day={day} />
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
