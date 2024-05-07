import styles from '../../styles/common/Modal.module.css';
import Header from './Header';

interface ModalProps {
  title: string;
  clickEvent: () => void;
  children: JSX.Element;
}
function Modal({ title, clickEvent, children }: ModalProps) {
  return (
    <>
      <div className={styles.modal}>
        <div className={styles.modal_content}>
          <div onClick={clickEvent} className={styles.close_button}>
            x
          </div>
          <Header title={title}/>
          {children}
        </div>
      </div>
      <div className={styles.overlay}></div>
    </>
  );
}

export default Modal;
