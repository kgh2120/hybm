import styles from "../../styles/common/ConfirmModal.module.css";
import Button from "./Button";

interface ConfirmModalProps {
  content: string;
  option1: string;
  option2: string;
  option1Event: () => void;
  option2Event: () => void;
}
function ConfirmModal({
  content,
  option1,
  option2,
  option1Event,
  option2Event,
}: ConfirmModalProps) {
  return (
    <>
      <div className={styles.modal}>
        <div className={styles.modal_content}>
          <span>{content}</span>
          <div className={styles.button_box}>
            <Button
              content={option1}
              color="red"
              onClick={option1Event}
            />
            <Button
              content={option2}
              color="white"
              onClick={option2Event}
            />
          </div>
        </div>
      </div>
      <div className={styles.overlay}></div>
    </>
  );
}

export default ConfirmModal;
