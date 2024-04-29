import styles from "../../styles/common/Modal.module.css";
import line from "../../assets/line.png";

interface propsType {
  title: string;
  clickEvent: () => void;
  children: JSX.Element;
}
function Modal({ title, clickEvent, children }: propsType) {
  return (
    <>
      <div className={styles.modal}>
        <div className={styles.modalContent}>
          <header>
            <div className={styles.left}>알림함 비우기</div>
            <h1>{title}</h1>
            <div onClick={clickEvent} className={styles.closeButton}>
              x
            </div>
          </header>
          <img className={styles.line} src={line} alt="" />
          {children}
        </div>
      </div>
      <div className={styles.overlay}></div>
    </>
  );
}

export default Modal;
