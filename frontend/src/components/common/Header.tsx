import styles from "../../styles/common/Header.module.css";
import line from "../../assets/images/line.png";

interface HeaderProps {
  title: string;
  // children: JSX.Element;
}

function Header({ title }: HeaderProps) {
  return (
    <header className={styles.wrapper}>
      <h1>{title}</h1>
      <img className={styles.line} src={line} alt="" />
    </header>
  );
}

export default Header;
