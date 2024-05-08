import styles from "../../styles/common/FoodStateSection.module.css"

interface FoodStateSectionProps {
  sectionTitle: string;
  sectionClass: string;
  children: React.ReactNode;
}

function FoodStateSection({
  sectionTitle,
  sectionClass,
  children,
}: 
FoodStateSectionProps) {
  return (
    <div className={styles.wrapper}>
      <h2>{sectionTitle}</h2>
      <section className={styles[sectionClass]}>{children}</section>
    </div>
  );
}

export default FoodStateSection;
