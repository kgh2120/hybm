import styles from '../../styles/common/ConfirmModal.module.css'
import Button from './Button';

interface propsType {
  content: string;
  option1: string;
  option2: string;
  option1Event: () => void;
  option2Event: () => void;
}
function ConfirmModal({content, option1, option2, option1Event, option2Event}: propsType) {
  return (
    <div className={styles.wrapper}>
      <span>{content}</span>
      <div className={styles.button_box}>
        <Button content={option1} color='red' clickEvent={option1Event}/>
        <Button content={option2} color='white' clickEvent={option2Event}/>
      </div>
    </div>
  )
}

export default ConfirmModal