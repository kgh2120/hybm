import { ReactNode } from "react";
import styles from "../../styles/landingPage/CustomButton.module.css";

interface CustomButtonProps {
  children: ReactNode;
  option: string;
  onClick: () => void;
}

function CustomButton({
  children,
  option,
  onClick,
}: CustomButtonProps) {
  return (
    <div className={styles.button_wrapper}>
      <button className={styles[option]} onClick={onClick}>
        {children}
      </button>
    </div>
  );
}

export default CustomButton;
