import styles from '../../styles/common/Button.module.css'

interface propsType {
  content: string;
  color: string;
}
function Button({content, color}: propsType) {
  return (
    <button className={color === 'red' ? styles.red_button : styles.white_button}>{content}</button>
  )
}

export default Button