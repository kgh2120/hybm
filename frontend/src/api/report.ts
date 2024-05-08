import instance from "./axios";

interface GetReportDataProps {
  year: number;
  month: number;
}

// 월간 지출 조회
const getReportData = async ({ year, month }: GetReportDataProps) => {
  try {
    const res = await instance.get(
      `/api/statistics/month?year=${year}&&month=${month}`
    );
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export { getReportData };
