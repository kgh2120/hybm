import styles from "../../styles/common/CategoryBox.module.css";
// import search from "../../assets/images/search.png";
import search from "../../assets/images/search.png";
import { ChangeEvent, useEffect, useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { getExpiredDate } from "../../api/foodApi";
import useFoodStore, {
  useFoodCategoryStore,
} from "../../stores/useFoodStore";

interface FilteredCategory {
  categoryImgSrc: string;
  name: string;
  bigName: string;
  categoryId: number;
}

interface CategoryBoxProps {
  onCategoryIdChange: (categoryId: number) => void;
}

interface CategoryType {
  categoryBigId: number;
  name: string;
  bigCategoryImgSrc: string;
  categoryDetails: CategoryDetailType[];
}

interface CategoryDetailType {
  categoryId: number;
  name: string;
  categoryImgSrc: string;
}

function CategoryBox({ onCategoryIdChange }: CategoryBoxProps) {
  const { bigCategoryList } = useFoodCategoryStore();
  const { inputList } = useFoodStore();
  const [name, setName] = useState("");
  const [selectedCategoryImgSrc, setSelectedCategoryImgSrc] =
    useState("");
  const [filteredCategoryList, setFilteredCategoryList] = useState<
    FilteredCategory[]
  >([]);

  const { mutate: mutateGetExpiredDate } = useMutation({
    mutationFn: getExpiredDate,
  });

  const handleChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };

  const handleSelectCategory = (category: FilteredCategory) => {
    const newName = `${category.name}(${category.bigName})`;
    setName(newName);
    const categoryId = category.categoryId;
    onCategoryIdChange(categoryId); // 부모 컴포넌트로 categoryId 전달
    mutateGetExpiredDate(categoryId);
    setSelectedCategoryImgSrc(category.categoryImgSrc); // 선택한 카테고리 이미지 설정
  };
  

  useEffect(() => {
    if (name.length >= 1) {
      changeFilteredCategoryList();
    } else {
      setFilteredCategoryList([]);
      setSelectedCategoryImgSrc("");
    }
  }, [name]);

  const changeFilteredCategoryList = () => {
    const updatedCategoryList = bigCategoryList.flatMap(
      (category) => {
        const filteredDetails = category.categoryDetails.filter(
          (detail) => detail.name.includes(name)
        );
        return filteredDetails.map((detail) => ({
          categoryId: detail.categoryId,
          categoryImgSrc: detail.categoryImgSrc,
          name: detail.name,
          bigName: category.name,
        }));
      }
    );
    setFilteredCategoryList(updatedCategoryList);
  };

  // localStorage에서 데이터 가져오기
  useEffect(() => {
    const storedData = localStorage.getItem("foodCategoryList");

    if (storedData) {
      const parsedData = JSON.parse(storedData);
      const categoryBigId = inputList.categoryBigId;
      const categoryId = inputList.categoryId;

      const bigCategory = parsedData.state.bigCategoryList.find(
        (category: CategoryType) =>
          category.categoryBigId === categoryBigId
      );

      if (bigCategory) {
        // bigCategory에서 categoryDetails 배열을 찾음
        const categoryDetails = bigCategory.categoryDetails;

        // categoryDetails에서 categoryId와 일치하는 객체를 찾음
        const targetCategory = categoryDetails.find(
          (category: CategoryDetailType) =>
            category.categoryId === categoryId
        );

        // targetCategory가 존재하는지 확인
        if (targetCategory) {
          setSelectedCategoryImgSrc(targetCategory.categoryImgSrc);
          const selectedName = `${targetCategory.name}(${bigCategory.name})`;
          setName(selectedName);
        }
      }
    }
  }, [inputList.categoryBigId, inputList.categoryId]);

  return (
    <div className={styles.category_box}>
      {selectedCategoryImgSrc !== "" && (
        <img
          className={styles.category_img}
          src={selectedCategoryImgSrc}
          alt="카테고리 이미지"
        />
      )}
      <input
        type="text"
        value={name}
        onChange={handleChangeName}
        placeholder="바코드를 촬영하거나 검색해보세요"
      />
      <img
        className={styles.category_search_img}
        src={search}
        alt="돋보기 이미지"
      />
      <section className={styles.filtered_category_list_section}>
        {filteredCategoryList.map((category) => (
          <div className={styles.category_list}>
            <article
            onClick={() => handleSelectCategory(category)}
            key={category.categoryId}
            >
              {category.name}({category.bigName})
            </article>
          </div>
        ))}
      </section>
    </div>
  );
}

export default CategoryBox;
