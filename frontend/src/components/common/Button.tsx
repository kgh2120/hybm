import styles from "../../styles/common/Button.module.css";

interface ButtonProps {
  content: string;
  color: string;
  onClick: () => void;
}
function Button({content, color, onClick}: ButtonProps) {
  return (
    <button className={color === 'red' ? styles.red_button : styles.white_button} onClick={onClick}>{content}</button>
  )
}

export default Button