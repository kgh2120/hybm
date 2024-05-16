import styles from "../../styles/common/FoodStateSection.module.css"

interface FoodStateSectionProps {
  sectionTitle: string;
  sectionClass: string;
  wrapperClass: string;
  children: React.ReactNode;
}

function FoodStateSection({
  sectionTitle,
  sectionClass,
  wrapperClass,
  children,
}: 
FoodStateSectionProps) {
  return (
    <div className={styles[wrapperClass]}>
      <h2>{sectionTitle}</h2>
      <section className={styles[sectionClass]}>{children}</section>
    </div>
  );
}

export default FoodStateSection;
