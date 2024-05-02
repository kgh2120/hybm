import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
} from "chart.js";
import { Pie } from "react-chartjs-2";
import styles from "../styles/reportPage/ReportPage.module.css";
import ItemBox from "../components/common/ItemBox";
import MyDatePicker from "../components/reportPage/Calendar";

ChartJS.register(ArcElement, Tooltip, Legend);

const bestFoodChartData = {
  labels: ["육류", "음료", "기타", "면류", "과일류", "통조림"],
  datasets: [
    {
      // data를 크기순으로 정렬해서 보여주기
      data: [19, 12, 5, 3, 3, 2],
      backgroundColor: [
        "#e5333e",
        "#ff848c",
        "#ffecd9",
        "#fce6e7",
        "#c9c9c9",
        "#666666",
      ],
      borderColor: [
        "#ffffff",
        "#ffffff",
        "#ffffff",
        "#ffffff",
        "#ffffff",
        "#ffffff",
      ],
      borderWidth: 1,
    },
  ],
};

const eatenOrThrownChartData = {
  labels: ["먹은 것", "버린 것"],
  datasets: [
    {
      // data를 크기순으로 정렬해서 보여주기
      data: [8, 3],
      backgroundColor: ["#fce6e7", "#c9c9c9"],
      borderColor: ["#ffffff", "#ffffff"],
      borderWidth: 1,
    },
  ],
};

function ReportPage() {
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
          size: 15,
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
      <div className={styles.title}>보고서</div>
      <div className={styles.white_background}>
        <div>
          <MyDatePicker />
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>내 지출 통계</div>
          <div className={styles.sub_content}>
            <p className={styles.context}>
              <p>
                <span>3월</span>엔 냉장고 속에
              </p>
              <p>
                <span>294,000원</span>을 지출했어요.
              </p>
            </p>
            <div className={styles.best_food}>
              <p>
                <span>3월</span>의 우수 식품
              </p>
              <Pie data={bestFoodChartData} options={options} />
            </div>
            <p className={styles.alert}>
              3월에 가격을 정하지 않은 항목이 있어요!
            </p>
          </div>
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>내 식품 통계</div>
          <div className={styles.sub_content}>
            <p className={styles.context}>먹은 것 vs 버린 것</p>
            <div className={styles.eaten_thrown}>
              <Pie data={eatenOrThrownChartData} options={options} />
            </div>
            <div className={styles.top_five}>
              <div>
                <p>먹은 것 Top 5</p>
                <div className={styles.item_boxes}>
                  <ItemBox option="report" />
                  <ItemBox option="report" />
                  <ItemBox option="report" />
                  <ItemBox option="report" />
                  <ItemBox option="report" />
                </div>
              </div>
              <div>
                <p>버린 것 Top 5</p>
                <div className={styles.item_boxes}>
                  <ItemBox option="report" />
                  <ItemBox option="report" />
                  <ItemBox option="report" />
                  <ItemBox option="report" />
                  <ItemBox option="report" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReportPage;
