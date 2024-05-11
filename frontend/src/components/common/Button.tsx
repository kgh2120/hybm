import styles from "../../styles/common/Button.module.css";

interface ButtonProps {
  content: string;
  color: string;
  onClick: () => void;
  disabled: boolean
}
function Button({content, color, onClick, disabled}: ButtonProps) {
  return (
    <button className={color === 'red' ? styles.red_button : styles.white_button} onClick={onClick} disabled={disabled}>{content}</button>
  )
}

export default Button