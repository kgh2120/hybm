import { Link } from "react-router-dom";
import styles from "../styles/recipePage/RecipePage.module.css";
import HomeBtn from "../assets/images/home.png";
import ItemBox from "../components/common/ItemBox";
import EmptyBox from "../components/common/EmptyBox";
import RecipeBox from "../components/recipePage/RecipeBox";
import { useQuery } from "@tanstack/react-query";
import { getDangerFoodBySection } from "../api/recipeApi";
import FoodStateSection from "../components/storagePage/FoodStateSection";
import { useState } from "react";

interface DangerFoodInfoType {
  foodId: number;
  name: string;
  categoryImgSrc: string;
  dday: number;
}

interface DangerFoodBySectionType {
  ICE: DangerFoodInfoType[];
  COOL: DangerFoodInfoType[];
  CABINET: DangerFoodInfoType[];
}

function RecipePage() {
  const TITLE_LIST: { [key: string]: string } = {
    ice: "냉동실",
    cool: "냉장실",
    cabinet: "찬장",
  };

  const {
    data: dangerFoodBySection,
    isPending: isDangerFoodBySectionPending,
    isError: isDangerFoodBySectionError,
  } = useQuery<DangerFoodBySectionType>({
    queryKey: ["dangerFoodBySection"],
    queryFn: getDangerFoodBySection,
  });

  const [selectedFoodList, setSelectedFoodList] = useState<
    DangerFoodInfoType[]
  >([]);

  const handlePickFood = (selectedFood: DangerFoodInfoType) => {
    const newSelectedFoodList = [...selectedFoodList];
    newSelectedFoodList.push(selectedFood);
    setSelectedFoodList(newSelectedFoodList);
  };

  const handleDeleteFood = (selectedFood: DangerFoodInfoType) => {
    const updatedFoodList = selectedFoodList.filter(
      (food) => food.foodId != selectedFood.foodId
    );
    setSelectedFoodList(updatedFoodList);
  };

  if (isDangerFoodBySectionPending) {
    return <div>데이터 가져오는 중...</div>;
  }
  if (isDangerFoodBySectionError) {
    return <div>에러나는 중...</div>;
  }

  return (
    <div className={styles.wrapper}>
      <div className={styles.page_header}>
        <Link to="/">
          <img
            src={HomeBtn}
            className={styles.home_img}
            alt="홈버튼"
          />
        </Link>
        <span className={styles.title}>레시피</span>
      </div>
      <div className={styles.white_background}>
        <div className={styles.content}>
          <div className={styles.sub_content}>
            <p>
              나의 냉장고 속 <span>위험 단계</span> 식품들 입니다.
            </p>
            <p>
              <span>레시피를 추천</span>받아보세요.
            </p>
          </div>
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>식품 선택하기</div>
          <div className={styles.sub_content}>
            <p>레시피 속 재료가 될 식품을 선택합니다.</p>
            <section className={styles.main_section}>
              {Object.keys(dangerFoodBySection).map(
                (section) =>
                  dangerFoodBySection[section].length > 0 && (
                    <FoodStateSection
                      key={section} // React에서 배열의 각 요소에 고유한 key를 지정해야 함
                      sectionTitle={TITLE_LIST[section]}
                      sectionClass="danger"
                    >
                      {dangerFoodBySection[section].map(
                        (item: DangerFoodInfoType, idx: number) => (
                          <ItemBox
                            key={idx}
                            name={item.name}
                            content={`D${item.dday}`}
                            option="active"
                            imgSrc={item.categoryImgSrc}
                            onClick={() => handlePickFood(item)}
                          />
                        )
                      )}
                    </FoodStateSection>
                  )
              )}
            </section>
          </div>
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>식품 확인하기</div>
          <div className={styles.sub_content}>
            <p>선택한 재료들을 확인합니다.</p>
            <p>잘못 선택한 재료는 터치로 지워주세요.</p>
            <section className={styles.main_section}>
              <section style={{ border: "2px solid #a9a9a9" }}>
                {selectedFoodList.length > 0 ? (
                  selectedFoodList.map(
                    (
                      selectedFood: DangerFoodInfoType,
                      idx: number
                    ) => (
                      <div key={idx}>
                        <ItemBox
                          name={selectedFood.name}
                          content={`D${selectedFood.dday}`}
                          option="active"
                          imgSrc={selectedFood.categoryImgSrc}
                          onClick={() =>
                            handleDeleteFood(selectedFood)
                          }
                        />
                      </div>
                    )
                  )
                ) : (
                  <div className={styles.section_content}>
                    <EmptyBox />
                    <p>선택한 식품은 이 곳에서 표시됩니다.</p>
                  </div>
                )}
              </section>
            </section>
          </div>
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>레시피 선택하기</div>
          <div className={styles.sub_content}>
            <p>선택한 재료들로 만들 수 있는 레시피로 이동합니다.</p>
            <section className={styles.main_section}>
              <RecipeBox
                imgSrc={HomeBtn}
                title="레시피 제목이지롱"
                onClick={() => {}}
              />
            </section>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RecipePage;
