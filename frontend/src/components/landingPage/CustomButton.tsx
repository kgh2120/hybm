import React, { ReactNode } from "react";
import styles from "../../styles/landingPage/CustomButton.module.css";

interface CustomButtonProps {
  children: ReactNode;
  option?: "kakao" | "naver";
  onClick?: (e: React.MouseEvent) => void;
}

function CustomButton({
  children,
  option = "kakao",
}: CustomButtonProps) {
  return (
    <div className={styles.button_wrapper}>
      {option === "naver" ? (
        <button className={styles.naver}>{children}</button>
      ) : (
        <button className={styles.kakao}>{children}</button>
      )}
    </div>
  );
}

export default CustomButton;
