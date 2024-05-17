import { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import styles from "../styles/receiptPage/ReceiptPage.module.css";
import home from "../assets/images/home.png";
import useFoodStore, {
  useFoodCategoryStore,
} from "../stores/useFoodStore";
import useAuthStore from "../stores/useAuthStore";
import { useMutation, useQuery } from "@tanstack/react-query";
import { postFoodByReceipt, postReceipt } from "../api/receiptApi";
import CategoryBox from "../components/common/CategoryBox";
import { getExpiredDate } from "../api/foodApi";

const tempResultList = [
  { name: "다이소균일가(2,000)", cost: 2000 },
  { name: "삼진어묵", cost: 3780 },
  { name: "꼬마새송이", cost: 990 },
  { name: "오뚜기 카레(약간매운맛)", cost: 5380 },
];

interface InputReceiptType {
  name: string;
  categoryId: number;
  price: number;
  expiredDate: string;
  location: string;
}
function ReceiptPage() {
  const navigate = useNavigate();
  const { bigCategoryList } = useFoodCategoryStore();
  const { image } = useAuthStore();

  const {
    mutate: mutatePostReceipt,
    data: namePriceList,
    status,
  } = useMutation({
    mutationFn: postReceipt,
    onSuccess: (data) => {},
  });
  const [inputReceiptList, setInputReceiptList] = useState<InputReceiptType[]>([]);
  console.log(namePriceList);

  const { mutate: mutatePostFoodByReceipt } = useMutation({
    mutationFn: postFoodByReceipt,
    onSuccess: () => {
      navigate("/");
    },
  });

  const handlePostFoodByReceipt = () => {
    console.log(mutatePostFoodByReceipt);
    // mutatePostFoodByReceipt();
  };

  useEffect(() => {
    mutatePostReceipt(image!);
  }, []);
  console.log(bigCategoryList);

  const { storageName } = useParams() as { storageName: string };
  const [selectedLocation, setSelectedLocation] = useState("");

  const TITLE_LIST: { [key: string]: string } = {
    ice: "냉동칸",
    cool: "냉장칸",
    cabinet: "찬장",
  };

  const {
    inputList,
    setInputList,
    isSelected,
    setIsSelected,
    initialInputList,
  } = useFoodStore();

  const { foodName, categoryId, price, expiredDate } = inputList;

  const handleCategoryIdChange = (categoryId: number) => {
    setInputList({
      ...inputList,
      categoryId: categoryId, // 선택한 카테고리의 categoryId를 업데이트
    });
    setIsSelected(true);
  };

  useEffect(() => {
    setIsSelected(true);
  }, [categoryId]);

  // 소비기한 확인 api
  const { data: foodExpiredDate } = useQuery({
    queryKey: ["foodExpiredDate"],
    queryFn: () => getExpiredDate(categoryId),
    enabled: isSelected,
    gcTime: 0,
  });

  const handleInputList = (
    e: React.ChangeEvent<HTMLInputElement>, idx: number
  ) => {
    const { name, value } = e.target;
    let newValue = value;
    console.log(idx)
    if (name === "price") {
      // 입력값이 "0"으로 시작하고, 그 뒤에 다른 숫자가 오면 "0"을 제거
      if (/^0\d/.test(value)) {
        newValue = value.substring(1);
      }
      // 입력값이 숫자가 아닌 경우 또는 빈 문자열인 경우 "0"으로 처리
      newValue =
        isNaN(parseInt(newValue)) || newValue === "" ? "0" : newValue;
      if (parseInt(newValue) >= 100000000) {
        newValue = newValue.slice(0, 8);
      }
    }

    setInputList({
      ...inputList,
      [name]: newValue,
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

  const handleLocationChange = (
    e: React.ChangeEvent<HTMLSelectElement>
  ) => {
    let newLocation = "";

    switch (e.target.value) {
      case "냉동칸":
        newLocation = "ICE";
        break;
      case "냉장칸":
        newLocation = "COOL";
        break;
      case "찬장":
        newLocation = "CABINET";
        break;
      default:
        newLocation = e.target.value;
    }
    setSelectedLocation(e.target.value);
    setInputList({
      ...inputList,
      location: newLocation,
    });
  };

  useEffect(() => {
    if (foodExpiredDate && categoryId) {
      setInputList({
        ...inputList,
        expiredDate: {
          year: foodExpiredDate.year,
          month: foodExpiredDate.month,
          day: foodExpiredDate.day,
        },
      });
    }
  }, [foodExpiredDate]);

  useEffect(() => {
    const initializedList = tempResultList.map(item => ({
      name: item.name,
      categoryId: 0,
      price: item.cost,
      expiredDate: "",
      location: ""
    }));
    setInputReceiptList(initializedList);
  }, []);

  if (status === "pending") {
    return <div>Loading...</div>;
  }

  if (inputReceiptList.length === 0) {
    return <div>Loading...</div>;
  }
  return (
    <div className={styles.wrapper}>
      <div className={styles.white_wrapper}>
        <Header title="영수증 등록" />
        <Link to="/">
          <img className={styles.home_img} src={home} alt="홈" />
        </Link>
        <section className={styles.food_list_section}>
          {inputReceiptList.map((_, idx) => {
            return (
              <div 
              key={idx}
              className={styles.food_section_wrapper}>
                <article className={styles.food_option_box}>
                  <span>상품명</span>
                  <input
                    name="foodName"
                    value={inputReceiptList[idx].name}
                    onChange={(e) => handleInputList(e, idx)}
                 
                  />
                </article>
                <article className={styles.food_option_box}>
                  <span>분류</span>
                  <CategoryBox
                    onCategoryIdChange={handleCategoryIdChange}
                  />
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
                      {Array.from(
                        { length: 12 },
                        (_, index) => index + 1
                      ).map((month) => (
                        <option key={month} value={month}>
                          {month}
                        </option>
                      ))}
                    </select>
                    <select
                      value={expiredDate.day}
                      onChange={handleDayChange}
                    >
                      {Array.from(
                        { length: 31 },
                        (_, index) => index + 1
                      ).map((day) => (
                        <option key={day} value={day}>
                          {day}
                        </option>
                      ))}
                    </select>
                  </div>
                </article>
                <article className={styles.food_option_box}>
                  <span>가격</span>
                  <div className={styles.price_box}>
                    <input
                      name="price"
                      type="number"
                      value={inputReceiptList[idx].price}
                      onChange={handleInputList}
                    />
                    <span>원</span>
                  </div>
                </article>
                <article className={styles.food_option_box}>
                  <span>위치</span>

                  <select
                    name="location"
                    value={
                      selectedLocation === ""
                        ? inputList.location
                        : selectedLocation
                    }
                    onChange={handleLocationChange}
                  >
                    <option value="냉동칸">냉동칸</option>
                    <option value="냉장칸">냉장칸</option>
                    <option value="찬장">찬장</option>
                  </select>
                </article>
              </div>
            );
          })}
        </section>
        <Button
          content="완료"
          color="red"
          onClick={handlePostFoodByReceipt}
          disabled={false}
        />
      </div>
    </div>
  );
}

export default ReceiptPage;
