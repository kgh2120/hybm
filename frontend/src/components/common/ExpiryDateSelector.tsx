import { useState, useEffect } from 'react';
import styles from '../../styles/common/ExpiryDateSelector.module.css'
interface ExpiryDate {
  year: number;
  month: number;
  day: number;
}

function ExpiryDateSelector() {
  const [expiryDate, setExpiryDate] = useState<ExpiryDate>({
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    day: new Date().getDate()
  });
  const [years, setYears] = useState<number[]>([]);

  useEffect(() => {
    const currentYear = new Date().getFullYear();
    const yearOptions: number[] = [];
    for (let i = currentYear - 1; i <= currentYear + 10; i++) {
      yearOptions.push(i);
    }
    setYears(yearOptions);
  }, []);

  const handleYearChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setExpiryDate({ ...expiryDate, year: parseInt(e.target.value) });
  };

  const handleMonthChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setExpiryDate({ ...expiryDate, month: parseInt(e.target.value) });
  };

  const handleDayChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    setExpiryDate({ ...expiryDate, day: parseInt(e.target.value) });
  };

  return (
    <div className={styles.wrapper}>
      <select value={expiryDate.year} onChange={handleYearChange}>
        {years.map((year) => (
          <option key={year} value={year}>
            {year}
          </option>
        ))}
      </select>
      <select value={expiryDate.month} onChange={handleMonthChange}>
        {Array.from({ length: 12 }, (_, index) => index + 1).map((month) => (
          <option key={month} value={month}>
            {month}
          </option>
        ))}
      </select>
      <select value={expiryDate.day} onChange={handleDayChange}>
        {Array.from({ length: 31 }, (_, index) => index + 1).map((day) => (
          <option key={day} value={day}>
            {day}
          </option>
        ))}
      </select>
    </div>
  );
};

export default ExpiryDateSelector;
