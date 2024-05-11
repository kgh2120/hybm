import { Link } from "react-router-dom";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import styles from "../styles/receiptPage/ReceiptPage.module.css";
import home from "../assets/images/home.png";
// import { useInput } from "../hooks/useInput";
// import { useState } from "react";
import FoodSection from "../components/common/FoodSection";
import { useFoodCategoryStore } from "../stores/useFoodStore";

function ReceiptPage() {
  const { bigCategoryList } = useFoodCategoryStore();
  console.log(bigCategoryList);

  return (
    <div className={styles.wrapper}>
      <div className={styles.white_wrapper}>
        <Header title="영수증 등록" />
        <Link to="/">
          <img className={styles.home_img} src={home} alt="" />
        </Link>
        <section className={styles.food_list_section}>
          <FoodSection option="active"/>

          <FoodSection option="active"/>
          <FoodSection option="active"/>
        </section>
        <Button content="완료" color="red" onClick={() => {}} disabled={false}/>
      </div>
    </div>
  );
}

export default ReceiptPage;
