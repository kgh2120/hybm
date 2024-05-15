import { Link } from "react-router-dom";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import styles from "../styles/receiptPage/ReceiptPage.module.css";
import home from "../assets/images/home.png";
import FoodSection from "../components/common/FoodSection";
import { useFoodCategoryStore } from "../stores/useFoodStore";
import useAuthStore from "../stores/useAuthStore";
import { useMutation } from "@tanstack/react-query";
import { postReceipt } from "../api/receiptApi";
import { useEffect } from "react";

function ReceiptPage() {
  const { bigCategoryList } = useFoodCategoryStore();
  const { imagePath } = useAuthStore();

  const { mutate: mutatePostReceipt, data: namePriceList } = useMutation({
    mutationFn: postReceipt,
  });

  useEffect(() => {
    mutatePostReceipt(imagePath);
  }, [namePriceList])
  console.log(bigCategoryList);

  return (
    <div className={styles.wrapper}>
      <div className={styles.white_wrapper}>
        <Header title="영수증 등록" />
        <Link to="/">
          <img className={styles.home_img} src={home} alt="" />
        </Link>
        <section className={styles.food_list_section}>
          <FoodSection option="receipt"/>

          <FoodSection option="receipt"/>
          <FoodSection option="receipt"/>
        </section>
        <Button content="완료" color="red" onClick={() => {}} disabled={false}/>
      </div>
    </div>
  );
}

export default ReceiptPage;
