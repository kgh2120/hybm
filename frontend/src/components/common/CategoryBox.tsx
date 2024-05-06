import styles from "../../styles/common/CategoryBox.module.css";
import search from "../../assets/search.png";
import { ChangeEvent, useEffect, useState } from "react";
import useFoodStore from "../../stores/useFoodStore";
import { useMutation, useQuery } from '@tanstack/react-query';
import { getExpiredDate } from '../../api/foodApi';

interface Category {
  categoryId: number;
  name: string;
  categoryImgSrc: string;
}

interface BigCategory {
  categoryBigId: number;
  name: string;
  bigCategoryImgSrc: string;
  categoryDetails: Category[];
}

interface FilteredCategory {
  categoryImgSrc: string;
  name: string;
  bigName: string;
  categoryId: number;
}

function CategoryBox() {
  const { bigCategoryList } = useFoodStore();
  const [name, setName] = useState("");
  const [selectedCategory, setSelectedCategory] =
    useState<FilteredCategory | null>(null);
  const [filteredCategoryList, setFilteredCategoryList] = useState<
    FilteredCategory[]
  >([]);

  const { mutate: mutateGetExpiredDate } = useMutation({
    mutationFn: getExpiredDate,
    
  })

  const handleChangeName = (e: ChangeEvent<HTMLInputElement>) => {
    setName(e.target.value);
  };

  const handleSelectCategory = (category: FilteredCategory) => {
    setSelectedCategory(category);
    const newName = `${category.name}(${category.bigName})`;
    setName(newName);
    const categoryId = category.categoryId;
    mutateGetExpiredDate(categoryId)
  };

  useEffect(() => {
    if (selectedCategory !== null) {
    }
  }, [selectedCategory]);

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

  function changeFilteredCategoryList() {
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
  }

  return (
    <div className={styles.category_box}>
      {selectedCategory?.categoryImgSrc !== "" && (
        <img
          className={styles.category_img}
          src={selectedCategory?.categoryImgSrc}
          alt="카테고리 이미지"
        />
      )}
      <input type="text" value={name} onChange={handleChangeName} />
      <img
        className={styles.category_search_img}
        src={search}
        alt=""
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
