import { Chart as ChartJS, ArcElement, Tooltip, Legend } from "chart.js";
import { Pie } from "react-chartjs-2";
import styles from "../styles/reportPage/reportPage.module.css";

ChartJS.register(ArcElement, Tooltip, Legend);

export const data = {
  labels: ["육류", "음료", "기타", "면류", "과일류", "통조림"],
  datasets: [
    {
      label: "# of Votes",
      data: [12, 19, 3, 5, 2, 3],
      backgroundColor: [
        "rgba(229, 51, 62, 1)",
        "rgba(251, 88, 98, 1)",
        "rgba(252, 230, 231, 1)",
        "rgba(201, 201, 201, 1)",
        "rgba(102, 102, 102, 1)",
        "rgba(255, 159, 64, 0.2)",
      ],
      borderColor: [
        "rgba(255, 99, 132, 1)",
        "rgba(54, 162, 235, 1)",
        "rgba(255, 206, 86, 1)",
        "rgba(75, 192, 192, 1)",
        "rgba(153, 102, 255, 1)",
        "rgba(255, 159, 64, 1)",
      ],
      borderWidth: 1,
    },
  ],
};

function ReportPage() {
  return (
    <div className={styles.wrapper}>
      <div className={styles.title}>보고서</div>
      <div className={styles.white_background}>
        <div>3월</div>
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
              {/* <div>차트 있을자리임</div> */}
              <Pie data={data} />
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
            <div className={styles.versus}>
              <div>차트 있을자리임</div>
            </div>
            <div className={styles.top_five}>
              <div>
                <p>먹은 것 Top 5</p>
                <div>똬똬똬똬똬</div>
              </div>
              <div>
                <p>버린 것 Top 5</p>
                <div>똬똬똬똬똬</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReportPage;
