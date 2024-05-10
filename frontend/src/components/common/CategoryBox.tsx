import styles from "../../styles/common/CategoryBox.module.css";
// import search from "../../assets/images/search.png";
import search from "../../assets/images/search.png";
import { ChangeEvent, useEffect, useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { getExpiredDate } from "../../api/foodApi";
import { useFoodCategoryStore } from "../../stores/useFoodStore";

interface FilteredCategory {
  categoryImgSrc: string;
  name: string;
  bigName: string;
  categoryId: number;
}

interface CategoryBoxProps {
  onCategoryIdChange: (categoryId: number) => void;
}

function CategoryBox({ onCategoryIdChange }: CategoryBoxProps) {
  const { bigCategoryList } = useFoodCategoryStore();
  const [name, setName] = useState("");
  const [selectedCategory, setSelectedCategory] =
    useState<FilteredCategory | null>(null);
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
    setSelectedCategory(category);
    const newName = `${category.name}(${category.bigName})`;
    setName(newName);
    const categoryId = category.categoryId;
    onCategoryIdChange(categoryId); // 부모 컴포넌트로 categoryId 전달
    mutateGetExpiredDate(categoryId);
  };

  useEffect(() => {
    if (name.length >= 1) {
      changeFilteredCategoryList();
    } else {
      setFilteredCategoryList([]);
      setSelectedCategory({
        categoryImgSrc: "",
        name: "",
        bigName: "",
        categoryId: 0,
      });
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

  return (
    <div className={styles.category_box}>
      {selectedCategory?.categoryImgSrc !== "" && (
        <img
          className={styles.category_img}
          src={selectedCategory?.categoryImgSrc}
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
          <article
            onClick={() => handleSelectCategory(category)}
            key={category.categoryId}
          >
            {category.name}({category.bigName})
          </article>
        ))}
      </section>
    </div>
  );
}

export default CategoryBox;
