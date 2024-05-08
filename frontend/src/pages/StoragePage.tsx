import { useState } from "react";
import Modal from "../components/common/Modal";
import CreateFoodModal from "../components/storagePage/CreateFoodModal";
import styles from "../styles/storagePage/StoragePage.module.css";
import { Link, useParams } from "react-router-dom";
import home from "../assets/images/home.png";
import edit from "../assets/images/edit.png";
import plus from "../assets/images/plus.png";
import ItemBox from "../components/common/ItemBox";
import FoodStateSection from "../components/storagePage/FoodStateSection";
import { getFoodStorageItemList } from "../api/foodApi";
import { useQuery } from "@tanstack/react-query";

function StoragePage() {
  const [isCreateFoodModalOpen, setIsCreateFoodModalOpen] =
    useState(false);
  const { storageName } = useParams() as { storageName: string };
  const handleOpenCreateFoodModal = () => {
    setIsCreateFoodModalOpen(true);
  };

  const handleCloseCreateFoodModal = () => {
    setIsCreateFoodModalOpen(false);
  };

  const TITLE_LIST: { [key: string]: string } = {
    ice: "냉동실",
    cool: "냉장실",
    cabinet: "찬장",
  };

  const SECTION_TITLE_LIST: { [key: string]: string } = {
    rotten: "소비기한 지남 (D+)😥",
    danger: "위험! (D-3)",
    warning: "경고 (D-7)",
    fresh: "신선😊",
  };

  interface FoodItemType {
    foodId: number;
    name: string;
    categoryImgSrc: string;
    dDay: number;
  }

  // 내부 식품 칸별 조회 api
  const {
    data: foodStorageItemList,
    isPending: isFoodStorageItemListPending,
    isError: isFoodStorageItemListError,
  } = useQuery({
    queryKey: ["foodStorageItemList"],
    queryFn: () => getFoodStorageItemList(storageName),
  });

  if (isFoodStorageItemListPending) {
    return <div>isLoding...</div>;
  }
  if (isFoodStorageItemListError) {
    return <div>error</div>;
  }

  console.log("아이템 리스트:", foodStorageItemList);

  return (
    <div className={styles.wrapper}>
      <div className={styles.white_wrapper}>
        <h1>{TITLE_LIST[storageName]}</h1>
        <Link to="/">
          <img
            className={styles.home_img}
            src={home}
            alt="홈 이미지"
          />
        </Link>
        <section className={styles.main_section}>
          {Object.keys(foodStorageItemList).map((sectionTitle) => (
            <FoodStateSection
              sectionTitle={SECTION_TITLE_LIST[sectionTitle]}
              sectionClass={sectionTitle}
            >
              {foodStorageItemList[sectionTitle].map(
                (item: FoodItemType) => (
                  <ItemBox
                    name={item.name}
                    content={`D-${item.dDay}`}
                    option={
                      sectionTitle === "rotten" ? "inactive" : ""
                    }
                    imgSrc={item.categoryImgSrc}
                  />
                )
              )}
            </FoodStateSection>
          ))}
        </section>
        <section className={styles.btn_section}>
          <div className={styles.btn_box}>
            <img src={edit} alt="" />
            <span>편집</span>
          </div>
          <div
            className={styles.btn_box}
            onClick={handleOpenCreateFoodModal}
          >
            <img src={plus} alt="" />
            <span>식품 추가</span>
          </div>
        </section>
      </div>
      {isCreateFoodModalOpen && (
        <Modal
          title="식품 등록"
          clickEvent={handleCloseCreateFoodModal}
        >
          <CreateFoodModal />
        </Modal>
      )}
    </div>
  );
}

export default StoragePage;
