import { useState } from "react";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import styles from "../../styles/reportPage/Calendar.module.css";

interface CustomHeaderProps {
  date: Date;
  decreaseYear: () => void;
  increaseYear: () => void;
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

function MyDatePicker() {
  const [selectedDate, setSelectedDate] = useState<Date | null>(
    new Date()
  );

  return (
    <DatePicker
      showMonthYearPicker
      shouldCloseOnSelect
      selected={selectedDate}
      onChange={(date) => setSelectedDate(date)}
      dateFormat="yyyy년 MM월"
      minDate={new Date("2023.12")}
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
