import styles from "../../styles/common/Button.module.css";

interface propsType {
  content: string;
  color: string;
  clickEvent: () => void;
}
function Button({content, color, clickEvent}: propsType) {
  return (
    <button className={color === "red" ? styles.red_button : styles.white_button} onClick={clickEvent}>{content}</button>
  )
}

export default Button