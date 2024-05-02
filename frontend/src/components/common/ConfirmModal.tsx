import styles from '../../styles/common/ConfirmModal.module.css'
import Button from './Button';

interface propsType {
  content: string;
  option1: string;
  option2: string;
}
function ConfirmModal({content, option1, option2}: propsType) {
  return (
    <div className={styles.wrapper}>
      <span>{content}</span>
      <div className={styles.button_box}>
        <Button content={option1} color='red'/>
        <Button content={option2} color='white'/>
      </div>
    </div>
  )
}

export default ConfirmModal