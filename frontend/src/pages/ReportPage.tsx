import { Link } from "react-router-dom";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
  ChartType,
} from "chart.js";
import { Chart } from "react-chartjs-2";
import ChartDataLabels from "chartjs-plugin-datalabels";
import styles from "../styles/reportPage/ReportPage.module.css";
import ItemBox from "../components/common/ItemBox";
import MyDatePicker from "../components/reportPage/Calendar";
import HomeBtn from "../assets/images/home.png";
import { getReportData } from "../api/reportApi";
import { useQuery } from "@tanstack/react-query";
import { useState, useEffect } from "react";
import EmptySection from "../components/common/EmptySection";

interface FoodType {
  bigCategory: string;
  spend: number;
}

interface DetailCategoryType {
  DetailCategoryId: number;
  name: string;
  imgSrc: string;
}

interface ReportDataType {
  totalSpend: number;
  hasEmptyPrice: boolean;
  spendByBigCategory: FoodType[];
  eatenCount: number;
  thrownCount: number;
  topEatenDetailCategory: DetailCategoryType[];
  topThrownDetailCategory: DetailCategoryType[];
}

interface HandleDateChangeParams {
  selectedYear: number;
  selectedMonth: number;
}

ChartJS.register(ArcElement, Tooltip, Legend);

function ReportPage() {
  // 이번 달의 년도와 월을 상태로 관리
  const [year, setYear] = useState<number>(new Date().getFullYear());
  const [month, setMonth] = useState<number>(
    new Date().getMonth() + 1
  );

  const {
    data: reportData,
    isPending: isReportDataPending,
    isError: isReportDataError,
    refetch: refetchReportData,
  } = useQuery<ReportDataType>({
    queryKey: ["reportData"],
    queryFn: () => getReportData({ year, month }),
  });

  useEffect(() => {
    // year 또는 month가 변경될 때마다 데이터를 다시 가져옴
    refetchReportData();
  }, [year, month]);

  if (isReportDataPending) {
    return <div>로그인 중...</div>;
  }

  if (isReportDataError) {
    return <div>에러나는 중...</div>;
  }

  // 날짜 변경 핸들러
  const handleDateChange = ({
    selectedYear,
    selectedMonth,
  }: HandleDateChangeParams) => {
    setYear(selectedYear);
    setMonth(selectedMonth);
    refetchReportData();
  };

  // 지출통계 코드
  const totalSpend: number = reportData.totalSpend;
  const totalSpendFormatted: string = totalSpend
    ? totalSpend.toLocaleString()
    : "";

  const spendByBigCategory: FoodType[] =
    reportData.spendByBigCategory;

  // 음식 비용별 Array
  const spendList: number[] =
    spendByBigCategory.length >= 1
      ? spendByBigCategory.map((food: FoodType) => food.spend)
      : [0, 0, 0, 0, 0, 0];

  // 음식 분류별 Array
  const bigCategoryList: string[] =
    spendByBigCategory.length >= 1
      ? spendByBigCategory.map((food: FoodType) => food.bigCategory)
      : ["데이터가 없습니다"];

  // 퍼센트로 계산 후 소수점 버리기
  const pricePercentage: number[] = spendList.map((spend: number) =>
    Math.floor((spend / totalSpend) * 100)
  );

  const bestFoodChartData = {
    type: "pie" as ChartType,
    labels: bigCategoryList,
    datasets: [
      {
        data: pricePercentage,
        backgroundColor: [
          "#e5333e",
          "#ff848c",
          "#ffecd9",
          "#fce6e7",
          "#c9c9c9",
          "#666666",
        ],
        borderColor:
          bigCategoryList.length <= 1 ? ["transparent"] : ["#ffffff"],
        borderWidth: 1,
      },
    ],
  };

  // 식품통계 코드
  const eatenCount = reportData.eatenCount;
  const thrownCount = reportData.thrownCount;

  const showlabels =
    eatenCount === 0
      ? ["버린 것"]
      : thrownCount === 0
      ? ["먹은 것"]
      : ["먹은 것", "버린 것"];

  const showData =
    eatenCount === 0
      ? [thrownCount]
      : thrownCount === 0
      ? [eatenCount]
      : [eatenCount, thrownCount];

  const backgroundColor =
    eatenCount === 0
      ? ["#c9c9c9"]
      : thrownCount === 0
      ? ["#fce6e7"]
      : ["#fce6e7", "#c9c9c9"];

  const eatenOrThrownChartData = {
    type: "pie" as ChartType,
    labels: showlabels,
    datasets: [
      {
        data: showData,
        backgroundColor: backgroundColor,
        borderColor:
          eatenCount === 0 || thrownCount === 0
            ? ["transparent"]
            : ["#ffffff", "#ffffff"],
        borderWidth: 1,
      },
    ],
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        display: true,
        usePointStyle: true,
        labels: {
          font: {
            size: 11,
            family: "PFStardust",
          },
          boxWidth: 10,
        },
      },
      datalabels: {
        font: {
          size: 13,
          family: "PFStardust",
        },
        color: "#000000",
        formatter: function (value: number | string) {
          return value;
        },
        display: true,
      },
    },
  };

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
        <span className={styles.title}>보고서</span>
      </div>
      <div className={styles.white_background}>
        <div>
          <MyDatePicker
            year={year}
            month={month}
            onDateChange={handleDateChange}
          />
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>내 지출 통계</div>
          {spendByBigCategory.length === 0 ? (
            <div>
              <EmptySection
                content1="아직 통계를 낼 데이터가 없어요"
                content2="냉장고에 식품을 등록하면 이 곳에서 볼 수 있습니다"
              />
            </div>
          ) : (
            <div className={styles.sub_content}>
              <p className={styles.context}>
                <p>
                  <span>{month}월</span>엔 냉장고 속에
                </p>
                <p>
                  <span>{totalSpendFormatted}원</span>을 지출했어요.
                </p>
              </p>
              <div className={styles.best_food}>
                <p>
                  <span>{month}월</span>의 우수 식품
                </p>
                {totalSpend === 0 ? (
                  <div>
                    냉장고에 식품이 있지만 가격이 정해진 식품이 없어서
                    차트를 띄울 수 없어요.
                  </div>
                ) : (
                  <Chart
                    type="pie"
                    data={bestFoodChartData}
                    options={options}
                    plugins={[ChartDataLabels]}
                  />
                )}
              </div>
              {reportData.hasEmptyPrice && (
                <p className={styles.alert}>
                  3월에 가격을 정하지 않은 항목이 있어요!
                </p>
              )}
            </div>
          )}
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>내 식품 통계</div>
          {eatenCount === 0 && thrownCount === 0 ? (
            <div>
              <EmptySection
                content1="아직 통계를 낼 데이터가 없어요"
                content2="먹거나 버린 음식이 있으면 이 곳에서 볼 수 있습니다"
              />
            </div>
          ) : (
            <div className={styles.sub_content}>
              <div className={styles.eaten_thrown}>
                <Chart
                  type="pie"
                  data={eatenOrThrownChartData}
                  options={options}
                  plugins={[ChartDataLabels]}
                />
              </div>
              <div className={styles.top_five}>
                <div>
                  <p>먹은 것 Top 5</p>
                  {eatenCount === 0 ? (
                    <div className={styles.is_empty}>
                      먹은 음식이 없습니다
                    </div>
                  ) : (
                    <div className={styles.item_box_list}>
                      {reportData.topEatenDetailCategory.map(
                        (food: DetailCategoryType, idx: number) => (
                          <ItemBox
                            key={idx}
                            name={food.name}
                            content=""
                            option="report"
                            imgSrc={food.imgSrc}
                            onClick={() => {}}
                          />
                        )
                      )}
                    </div>
                  )}
                </div>
                <div>
                  <p>버린 것 Top 5</p>
                  {thrownCount === 0 ? (
                    <div className={styles.is_empty}>
                      버린 음식이 없습니다
                    </div>
                  ) : (
                    <div className={styles.item_box_list}>
                      {reportData.topThrownDetailCategory.map(
                        (food: DetailCategoryType, idx: number) => (
                          <ItemBox
                            key={idx}
                            name={food.name}
                            content=""
                            option="report"
                            imgSrc={food.imgSrc}
                            onClick={() => {}}
                          />
                        )
                      )}
                    </div>
                  )}
                </div>
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}

export default ReportPage;
