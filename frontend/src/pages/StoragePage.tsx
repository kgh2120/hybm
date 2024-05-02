import { useState } from "react";
import Modal from "../components/common/Modal";
import CreateFoodModal from "../components/storagePage/CreateFoodModal";
import styles from "../styles/storagePage/StoragePage.module.css";
import { Link, useParams } from "react-router-dom";
import home from "../assets/home.png";
import edit from "../assets/edit.png";
import plus from "../assets/plus.png";
import ItemBox from "../components/common/ItemBox";

function StoragePage() {
  const [isCreateFoodModalOpen, setIsCreateFoodModalOpen] =
    useState(false);
  const { storageName } = useParams();
  const handleOpenCreateFoodModal = () => {
    setIsCreateFoodModalOpen(true);
  };

  const handleCloseCreateFoodModal = () => {
    setIsCreateFoodModalOpen(false);
  };

  let title = "냉동실";
  if (storageName === "ice") {
    title = "냉동실";
  } else if (storageName === "cool") {
    title = "냉장실";
  } else if (storageName === "cabinet") {
    title = "찬장";
  }

  return (
    <div className={styles.wrapper}>
      <div className={styles.white_wrapper}>
        <h1>{title}</h1>
        <Link to="/">
          <img className={styles.home_img} src={home} alt="" />
        </Link>
        <section className={styles.main_section}>
          <div>
            <h2>소비기한 지남 (D+)😥</h2>
          </div>
          <section style={{ border: '2px solid #a9a9a9' }}>
            <ItemBox option="inactive" />
            <ItemBox option="inactive" />
            <ItemBox option="inactive" />
            <ItemBox option="inactive" />
            <ItemBox option="inactive" />
            <ItemBox option="inactive" />
          </section>
          <div>
            <h2>위험! (D-3)</h2>
          </div>
          <section style={{ border: '2px solid #ffa7a7' }}>
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
          </section>
          <div>
            <h2>경고 (D-7)</h2>
          </div>
          <section style={{ border: '2px solid #ffd66a' }}>
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
          </section>
          <div>
            <h2>신선😊</h2>
          </div>
          <section style={{ border: '2px solid #7dd086' }}>
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
            <ItemBox option="active" />
          </section>
        </section>
        <section className={styles.btn_section}>
          <div className={styles.btn_box}>
            <img src={edit} alt="" />
            <span>편집</span>
          </div>
          <div className={styles.btn_box}>
            <img src={plus} alt="" />
            <span onClick={handleOpenCreateFoodModal}>식품 추가</span>
          </div>
        </section>
      </div>
      {isCreateFoodModalOpen && (
        <Modal title="식품 등록" clickEvent={handleCloseCreateFoodModal}>
          <CreateFoodModal />
        </Modal>
      )}
    </div>
  );
}

export default StoragePage;
