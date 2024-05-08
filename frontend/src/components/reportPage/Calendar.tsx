import { useState } from "react";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import styles from "../../styles/reportPage/Calendar.module.css";
import { useQuery } from "@tanstack/react-query";
import { getUserSignUpDate } from "../../api/userApi";

interface CustomHeaderProps {
  date: Date;
  decreaseYear: () => void;
  increaseYear: () => void;
}

interface HandleDateChangeParams {
  selectedYear: number;
  selectedMonth: number;
}

interface MyDatePickerProps {
  year: number;
  month: number;
  onDateChange: ({
    selectedYear,
    selectedMonth,
  }: HandleDateChangeParams) => void;
}

function CustomHeader({
  date,
  decreaseYear,
  increaseYear,
}: CustomHeaderProps) {
  return (
    <div className={styles.custom_header}>
      <button onClick={decreaseYear} className={styles.custom_button}>
        <FaChevronLeft />
      </button>
      <span>
        {date.toLocaleString("default", {
          year: "numeric",
          month: "long",
        })}
      </span>
      <button onClick={increaseYear} className={styles.custom_button}>
        <FaChevronRight />
      </button>
    </div>
  );
}

function MyDatePicker({ year, month, onDateChange }: MyDatePickerProps) {
  const [selectedDate, setSelectedDate] = useState<Date>(
    new Date(year, month - 1)
  );

  // axios 요청 불러오기
  const {
    data: userSignUpDate,
    isPending: isUserSignUpPending,
    isError: isUserSignUpDateError,
  } = useQuery({
    queryKey: ["UserSignUpDate"],
    queryFn: getUserSignUpDate,
  });

  if (isUserSignUpPending) {
    return <div>데이터 가져오는 중...</div>;
  }

  if (isUserSignUpDateError) {
    return <div>에러나는 중...</div>;
  }

  const handleDateChange = (date: Date) => {
      setSelectedDate(date);
      const selectedYear = date.getFullYear();
      const selectedMonth = date.getMonth() + 1;
      onDateChange({ selectedYear, selectedMonth });
  };

  return (
    <DatePicker
      showMonthYearPicker
      shouldCloseOnSelect
      selected={selectedDate}
      onChange={(date) => {
        // ts에서 null체크 하려고 해서 넣은 as Date
        handleDateChange(date as Date);
      }}
      dateFormat="yyyy년 MM월"
      minDate={
        new Date(`${userSignUpDate.year}-${userSignUpDate.month}-01`)
      }
      maxDate={new Date()}
      className={styles.date_picker}
      renderCustomHeader={({ date, decreaseYear, increaseYear }) => (
        <CustomHeader
          date={date}
          decreaseYear={decreaseYear}
          increaseYear={increaseYear}
        />
      )}
      showIcon
      toggleCalendarOnIconClick
    />
  );
}

export default MyDatePicker;
