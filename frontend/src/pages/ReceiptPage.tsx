import { ChangeEvent, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import Button from "../components/common/Button";
import Header from "../components/common/Header";
import styles from "../styles/receiptPage/ReceiptPage.module.css";
import home from "../assets/images/home.png";
import search from "../assets/images/search.png";
import { useFoodCategoryStore } from "../stores/useFoodStore";
import useAuthStore from "../stores/useAuthStore";
import { useMutation, useQuery } from "@tanstack/react-query";
import { postFoodByReceipt, postReceipt } from "../api/receiptApi";
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

interface FilteredCategory {
  categoryImgSrc: string;
  name: string;
  bigName: string;
  categoryId: number;
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

interface ExpiredDateType {
  year: number;
  month: number;
  day: number;
}
function ReceiptPage() {
  const navigate = useNavigate();
  const { image } = useAuthStore();

  const {
    mutate: mutatePostReceipt,
    data: namePriceList,
    status,
  } = useMutation({
    mutationFn: postReceipt,
    onSuccess: (data) => {},
  });
  const [inputReceiptList, setInputReceiptList] = useState<
    InputReceiptType[]
  >([]);

  const { mutate: mutatePostFoodByReceipt } = useMutation({
    mutationFn: postFoodByReceipt,
    onSuccess: () => {
      navigate("/");
    },
  });

  const handlePostFoodByReceipt = () => {
    const newInputReceiptList = inputReceiptList.map(
      (inputReceipt, idx) => {
        const formattedMonth =
          expiredDateList[idx].month < 10
            ? `0${expiredDateList[idx].month}`
            : expiredDateList[idx].month;
        const formattedDay =
          expiredDateList[idx].day < 10
            ? `0${expiredDateList[idx].day}`
            : expiredDateList[idx].day;
        let formattedLocation = "";
        if (selectedLocation[idx] === "냉동칸") {
          formattedLocation = "ice";
        } else if (selectedLocation[idx] === "냉장칸") {
          formattedLocation = "cool";
        } else if (selectedLocation[idx] === "찬장") {
          formattedLocation = "cabinet";
        }
        return {
          ...inputReceipt,
          location: formattedLocation,
          expiredDate: `${expiredDateList[idx].year}-${formattedMonth}-${formattedDay}`,
          categoryId: categoryIdList[idx],
        };
      }
    );
    mutatePostFoodByReceipt(newInputReceiptList);
    // mutatePostFoodByReceipt();
  };

  useEffect(() => {
    mutatePostReceipt(image!);
  }, []);
  // console.log(bigCategoryList);

  const [selectedLocation, setSelectedLocation] = useState<string[]>(
    []
  );
  const [nameList, setNameList] = useState<string[]>([]);
  const [imgSrcList, setImgSrcList] = useState<string[]>([]);
  const [categoryIdList, setCategoryIdList] = useState<number[]>([]);
  const [selectedCategoryId, setSelectedCategoryId] = useState<
    number | null
  >(null);
  const [expiredDateList, setExpiredDateList] = useState<
    ExpiredDateType[]
  >([]);
  // const handleCategoryIdChange = (
  //   categoryId: number,
  //   idx: number
  // ) => {
  //   const updatedList = [...inputReceiptList];
  //   updatedList[idx] = {
  //     ...updatedList[idx],
  //     categoryId: categoryId,
  //   };
  //   setInputReceiptList(updatedList);
  //   setIsSelected(true);
  // };

  // useEffect(() => {
  //   setIsSelected(true);
  // }, [categoryId]);

  // 소비기한 확인 api
  // const { data: foodExpiredDate, refetch: refetchFoodExpiredDate } = useQuery({
  //   queryKey: ["foodExpiredDate"],
  //   queryFn: () => getExpiredDate(categoryId),
  //   gcTime: 0,
  // });

  const { mutate: mutateGetExpiredDate } = useMutation({
    mutationFn: getExpiredDate,
    onSuccess: (data) => {
      const newExpiredDateList = [...expiredDateList];
      newExpiredDateList[selectedCategoryId!].year = data.year;
      newExpiredDateList[selectedCategoryId!].month = data.month;
      newExpiredDateList[selectedCategoryId!].day = data.day;

      setExpiredDateList(newExpiredDateList);
    },
  });

  // useEffect(() => {
  //   if (selectedCategoryId !== null && expiredDate) {
  //     const newExpiredDateList = [...expiredDateList];
  //     newExpiredDateList[selectedCategoryId] = {
  //       year: expiredDate.year,
  //       month: expiredDate.month,
  //       day: expiredDate.day
  //     }
  //     console.log("test:",newExpiredDateList);
  //     setExpiredDateList(newExpiredDateList)
  //   }
  // }, [selectedCategoryId]);

  const handleInputList = (
    e: React.ChangeEvent<HTMLInputElement>,
    idx: number
  ) => {
    const { name, value } = e.target;
    let newValue = value;

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

    const updatedList = [...inputReceiptList];
    updatedList[idx] = { ...updatedList[idx], [name]: newValue };
    setInputReceiptList(updatedList);
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
    e: React.ChangeEvent<HTMLSelectElement>,
    idx: number
  ) => {
    const newExpiredDateList = [...expiredDateList];
    newExpiredDateList[idx].year = parseInt(e.target.value);
    setExpiredDateList(newExpiredDateList);
  };

  const handleMonthChange = (
    e: React.ChangeEvent<HTMLSelectElement>,
    idx: number
  ) => {
    const newExpiredDateList = [...expiredDateList];
    newExpiredDateList[idx].month = parseInt(e.target.value);
    setExpiredDateList(newExpiredDateList);
  };

  const handleDayChange = (
    e: React.ChangeEvent<HTMLSelectElement>,
    idx: number
  ) => {
    const newExpiredDateList = [...expiredDateList];
    newExpiredDateList[idx].day = parseInt(e.target.value);
    setExpiredDateList(newExpiredDateList);
  };

  const handleLocationChange = (
    e: React.ChangeEvent<HTMLSelectElement>,
    idx: number
  ) => {
    // let newLocation = "";

    // switch (e.target.value) {
    //   case "냉동칸":
    //     newLocation = "ICE";
    //     break;
    //   case "냉장칸":
    //     newLocation = "COOL";
    //     break;
    //   case "찬장":
    //     newLocation = "CABINET";
    //     break;
    //   default:
    //     newLocation = e.target.value;
    // }
    const newLocationList = [...selectedLocation];
    newLocationList[idx] = e.target.value;
    setSelectedLocation(newLocationList);

    // const updatedList = [...inputReceiptList];
    // updatedList[idx] = { ...updatedList[idx], location: newLocation };
    // setInputReceiptList(updatedList);
  };

  // useEffect(() => {
  //   if (foodExpiredDate && categoryId) {
  //     setInputList({
  //       ...inputList,
  //       expiredDate: {
  //         year: foodExpiredDate.year,
  //         month: foodExpiredDate.month,
  //         day: foodExpiredDate.day,
  //       },
  //     });
  //   }
  // }, [foodExpiredDate]);

  useEffect(() => {
    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    const currentMonth = currentDate.getMonth() + 1;
    const currentDay = currentDate.getDate();
    const initializedList = tempResultList.map((item) => {
      selectedLocation.push("냉동칸");
      nameList.push("");
      imgSrcList.push("");
      expiredDateList.push({
        year: currentYear,
        month: currentMonth,
        day: currentDay,
      });
      categoryIdList.push(0);
      return {
        name: item.name,
        categoryId: 0,
        price: item.cost,
        expiredDate: "",
        location: "",
      };
    });
    setInputReceiptList(initializedList);
  }, []);

  // 가져온 카테고리..
  const { bigCategoryList } = useFoodCategoryStore();

  const [selectedId, setSelectedId] = useState<number | null>(null);
  const [filteredCategoryList, setFilteredCategoryList] = useState<
    FilteredCategory[]
  >([]);

  const handleChangeName = (
    e: ChangeEvent<HTMLInputElement>,
    idx: number
  ) => {
    setSelectedId(idx);
    const newNameList = [...nameList];
    newNameList[idx] = e.target.value;
    setNameList(newNameList);
  };

  useEffect(() => {
    if (selectedId !== null && nameList[selectedId].length >= 1) {
      changeFilteredCategoryList();
    } else if (
      selectedId !== null &&
      nameList[selectedId].length < 1
    ) {
      setFilteredCategoryList([]);
      const newImgSrcList = [...imgSrcList];
      newImgSrcList[selectedId] = "";
      setImgSrcList(newImgSrcList);
    }
  }, [nameList]);

  const handleSelectCategory = (
    category: FilteredCategory,
    idx: number
  ) => {
    const newName = `${category.name}(${category.bigName})`;
    setSelectedCategoryId(idx);
    const newNameList = [...nameList];
    newNameList[idx] = newName;
    setNameList(newNameList);
    mutateGetExpiredDate(category.categoryId);

    const newImgSrcList = [...imgSrcList];
    newImgSrcList[idx] = category.categoryImgSrc;
    setImgSrcList(newImgSrcList);

    const newCategoryIdList = [...categoryIdList];
    newCategoryIdList[idx] = category.categoryId;
    setCategoryIdList(newCategoryIdList);
  };

  const changeFilteredCategoryList = () => {
    const updatedCategoryList = bigCategoryList.flatMap(
      (category) => {
        const filteredDetails = category.categoryDetails.filter(
          (detail) => detail.name.includes(nameList[selectedId!])
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
          {inputReceiptList.map((inputReceipt, idx) => {
            return (
              <div key={idx} className={styles.food_section_wrapper}>
                <article className={styles.food_option_box}>
                  <span>상품명</span>
                  <input
                    name="name"
                    value={inputReceipt.name}
                    onChange={(e) => handleInputList(e, idx)}
                  />
                </article>
                <article className={styles.food_option_box}>
                  <span>분류</span>
                  <div className={styles.category_box}>
                    {imgSrcList[idx] !== "" && (
                      <img
                        className={styles.category_img}
                        src={imgSrcList[idx]}
                        alt="카테고리 이미지"
                      />
                    )}
                    <input
                      type="text"
                      value={nameList[idx]}
                      onChange={(e) => handleChangeName(e, idx)}
                    />
                    <img
                      className={styles.category_search_img}
                      src={search}
                      alt="돋보기 이미지"
                    />
                    {selectedId === idx && (
                      <section
                        className={
                          styles.filtered_category_list_section
                        }
                      >
                        {filteredCategoryList.map((category) => (
                          <div
                            key={category.categoryId}
                            className={styles.category_list}
                          >
                            <article
                              onClick={() =>
                                handleSelectCategory(category, idx)
                              }
                              key={category.categoryId}
                            >
                              {category.name}({category.bigName})
                            </article>
                          </div>
                        ))}
                      </section>
                    )}
                  </div>
                </article>
                <article className={styles.food_option_box}>
                  <span>소비기한</span>
                  <div className={styles.expiry_date_box}>
                    <select
                      value={expiredDateList[idx].year}
                      onChange={(e) => handleYearChange(e, idx)}
                    >
                      {years.map((year) => (
                        <option key={year} value={year}>
                          {year}
                        </option>
                      ))}
                    </select>
                    <select
                      value={expiredDateList[idx].month}
                      onChange={(e) => handleMonthChange(e, idx)}
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
                      value={expiredDateList[idx].day}
                      onChange={(e) => handleDayChange(e, idx)}
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
                      value={inputReceipt.price}
                      onChange={(e) => handleInputList(e, idx)}
                    />
                    <span>원</span>
                  </div>
                </article>
                <article className={styles.food_option_box}>
                  <span>위치</span>

                  <select
                    name="location"
                    // value={
                    //   selectedLocation[idx] === ""
                    //     ? inputReceiptList[idx].location
                    //     : selectedLocation[idx]
                    // }
                    value={selectedLocation[idx]}
                    onChange={(e) => handleLocationChange(e, idx)}
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
