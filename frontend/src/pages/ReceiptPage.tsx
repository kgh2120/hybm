import { Link } from 'react-router-dom'
import Button from '../components/common/Button'
import FoodSection from '../components/common/FoodSection'
import Header from '../components/common/Header'
import styles from '../styles/receiptPage/ReceiptPage.module.css'
import home from "../assets/home.png";

function ReceiptPage() {
  return (
    <div className={styles.wrapper}>
      <div className={styles.white_wrapper}>
        <Header title="영수증 등록"/>
        <Link to="/">
          <img className={styles.home_img} src={home} alt="" />
        </Link>
        <section className={styles.food_list_section}>
          <FoodSection />
          <FoodSection />
          <FoodSection />
          <FoodSection />
        </section>
        <Button content="완료" color="red"/>
      </div>
    </div>
  )
}

export default ReceiptPage