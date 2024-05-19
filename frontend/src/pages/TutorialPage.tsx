import styles from "../styles/tutorialPage/TutorialPage.module.css";
import tutorialFridge from "../assets/images/tutorialFridge.png";
import tutorialDesign from "../assets/images/tutorialDesign.png";
import tutorialExpBar from "../assets/images/tutorialExpBar.png";
import tutorialReceipt from "../assets/images/tutorialReceipt.png";
import tutorialReport from "../assets/images/tutorialReport.png";
import tutorialRecipe from "../assets/images/tutorialRecipe.png";
import tutorialTrashCan from "../assets/images/tutorialTrashCan.png";
import Button from "../components/common/Button";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

function TutorialPage() {
  const navigate = useNavigate();
  const tutorialImageList = [tutorialFridge, tutorialDesign, tutorialExpBar, tutorialReceipt, tutorialReport, tutorialRecipe, tutorialTrashCan];
  const [currentIndex, setCurrentIndex] = useState(0);

  const handleNextClick = () => {
    if (currentIndex < tutorialImageList.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  const handleFinishClick = () => {
    navigate("/");
  };
  return (
    <div className={styles.wrapper}>
      <img
        src={tutorialImageList[currentIndex]}
        alt={`튜토리얼 이미지 ${currentIndex + 1}`}
      />
      <div className={styles.tutorial_button}>
        {currentIndex < tutorialImageList.length - 1 ? (
          <Button
            content="다음"
            color="red"
            onClick={handleNextClick}
            disabled={false}
          />
        ) : (
          <Button
            content="완료"
            color="red"
            onClick={handleFinishClick}
            disabled={false}
          />
        )}
      </div>
    </div>
  );
}

export default TutorialPage;
