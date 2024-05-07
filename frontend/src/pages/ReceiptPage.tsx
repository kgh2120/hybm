import { Link } from "react-router-dom";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import styles from "../styles/receiptPage/ReceiptPage.module.css";
import home from "../assets/home.png";
// import { useInput } from "../hooks/useInput";
// import { useState } from "react";
import FoodSection from "../components/common/FoodSection";
import useFoodStore from '../stores/useFoodStore';

function ReceiptPage() {
  const { bigCategoryList } = useFoodStore();
  console.log(bigCategoryList)
  // const [name, changeName] = useInput("닭고기");
  // const [categoryId, changeCategoryId] = useInput("닭고기");
  // const [price, setPrice] = useState(0);
  // const [expiredDate, changeExpiredDate] = useInput("닭고기");
  // const [location, changeLocation] = useInput("닭고기");
  // const [isManual, changeIsManual] = useInput("닭고기");

  return (
    <div className={styles.wrapper}>
      <div className={styles.white_wrapper}>
        <Header title="영수증 등록" />
        <Link to="/">
          <img className={styles.home_img} src={home} alt="" />
        </Link>
        <section className={styles.food_list_section}>
          <FoodSection
   
          />

          <FoodSection
 
          />
          <FoodSection
    
          />
        </section>
        <Button content="완료" color="red" />
      </div>
    </div>
  );
}

export default ReceiptPage;
