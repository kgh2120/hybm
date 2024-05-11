import { useEffect, useState } from "react";
import styles from "../../styles/common/FoodSection.module.css";
import CategoryBox from "./CategoryBox";
// import ExpiryDateSelector from "./ExpiryDateSelector";
import { getExpiredDate } from "../../api/foodApi";
import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import useFoodStore from "../../stores/useFoodStore";

interface FoodSectionProps {
  option: string;
}

function FoodSection({ option = "active" }: FoodSectionProps) {
  const { storageName } = useParams() as { storageName: string };

  const TITLE_LIST: { [key: string]: string } = {
    ice: "냉동실",
    cool: "냉장실",
    cabinet: "찬장",
  };

  const { inputList, setInputList } = useFoodStore();

  const { foodName, categoryId, expiredDate, price } = inputList;

  const handleCategoryIdChange = (categoryId: number) => {
    setInputList({
      ...inputList,
      categoryId: categoryId, // 선택한 카테고리의 categoryId를 업데이트
    });
  };

  const handleInputList = (
    e: React.ChangeEvent<HTMLInputElement>
  ) => {
    const { name, value } = e.target;
    setInputList({
      ...inputList,
      [name]: name === "price" ? parseFloat(value) || 0 : value,
    });
  };

  const [years, setYears] = useState<number[]>([]);

  useEffect(() => {
    const currentYear = new Date().getFullYear();
    const yearOptions: number[] = [];
    for (let i = currentYear - 1; i <= currentYear + 10; i++) {
      yearOptions.push(i);
    }
    setYears(yearOptions);
  }, []);

  const handleYearChange = (
    e: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setInputList({
      ...inputList,
      expiredDate: {
        ...inputList.expiredDate,
        year: parseInt(e.target.value),
      },
    });
  };

  const handleMonthChange = (
    e: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setInputList({
      ...inputList,
      expiredDate: {
        ...inputList.expiredDate,
        month: parseInt(e.target.value),
      },
    });
  };

  const handleDayChange = (
    e: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setInputList({
      ...inputList,
      expiredDate: {
        ...inputList.expiredDate,
        day: parseInt(e.target.value),
      },
    });
  };

  // 소비기한 확인 api
  const { refetch, data: foodExpiredDate } = useQuery({
    queryKey: ["foodExpiredDate"],
    queryFn: () => getExpiredDate(categoryId),
    enabled: false,
  });

  useEffect(() => {
    if (categoryId !== 0) {
      refetch();
      if (foodExpiredDate) {
        setInputList({
          ...inputList,
          expiredDate: {
            year: foodExpiredDate.year,
            month: foodExpiredDate.month,
            day: foodExpiredDate.day,
          },
        });
      }
    }
  }, [categoryId, refetch, foodExpiredDate]);

  return (
    <div className={styles.wrapper}>
      <article className={styles.food_option_box}>
        <span>상품명</span>
        <input
          name="foodName"
          value={foodName}
          onChange={handleInputList}
          placeholder="바코드를 촬영하거나 직접 입력해보세요"
        />
      </article>
      <article className={styles.food_option_box}>
        <span>분류</span>
        <CategoryBox onCategoryIdChange={handleCategoryIdChange} />
      </article>
      <article className={styles.food_option_box}>
        <span>소비기한</span>
        <div className={styles.expiry_date_box}>
          <select
            value={expiredDate.year}
            onChange={handleYearChange}
          >
            {years.map((year) => (
              <option key={year} value={year}>
                {year}
              </option>
            ))}
          </select>
          <select
            value={expiredDate.month}
            onChange={handleMonthChange}
          >
            {Array.from({ length: 12 }, (_, index) => index + 1).map(
              (month) => (
                <option key={month} value={month}>
                  {month}
                </option>
              )
            )}
          </select>
          <select value={expiredDate.day} onChange={handleDayChange}>
            {Array.from({ length: 31 }, (_, index) => index + 1).map(
              (day) => (
                <option key={day} value={day}>
                  {day}
                </option>
              )
            )}
          </select>
        </div>
      </article>
      <article className={styles.food_option_box}>
        <span>가격</span>
        <div className={styles.price_box}>
          <input
            name="price"
            value={price}
            onChange={handleInputList}
          />
          <span>원</span>
        </div>
      </article>
      <article className={styles.food_option_box}>
        <span>위치</span>
        {option === "active" ? (
          <select>
            <option value="냉동실">냉동실</option>
            <option value="냉장실">냉장실</option>
            <option value="찬장">찬장</option>
          </select>
        ) : (
          <div className={styles.storage_box}>
            {TITLE_LIST[storageName]}
          </div>
        )}
      </article>
    </div>
  );
}

export default FoodSection;
