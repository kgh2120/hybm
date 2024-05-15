const formatPrice = (price: number | string) => {
  return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ","); // 3자리마다 쉼표 추가
};

const formatDate = (date: string) => {
  const year = date.substring(0, 4);
  const month = date.substring(4, 6);
  const day = date.substring(6, 8);

  return `${year}.${month}.${day}`;
};

const stringToDate = (dateString: string) => {
  const year = dateString.substring(0, 4);
  const month = dateString.substring(4, 6);
  const day = dateString.substring(6, 8);

  return { year, month, day };
};

const dateToString = (year: number, month: number, day: number) => {
  const date = String(year) + String(month) + String(day);

  return date;
};

interface DateType {
  year: number;
  month: number;
  day: number;
}

const formatDashDate = (date: DateType) => {
  const { year, month, day } = date;
  const formattedMonth = month < 10 ? `0${month}` : `${month}`;
  const formattedDay = day < 10 ? `0${day}` : `${day}`;
  return `${year}-${formattedMonth}-${formattedDay}`;
};

const formatDashStringToDate = (dateString: string) => {
  const [year, month, day] = dateString.split("-").map(Number);
  return { year, month, day };
};
export {
  formatPrice,
  formatDate,
  stringToDate,
  dateToString,
  formatDashDate,
  formatDashStringToDate,
};
