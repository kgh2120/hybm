import { Link } from "react-router-dom";
import styles from "../styles/recipePage/RecipePage.module.css";
import HomeBtn from "../assets/images/home.png";
import ItemBox from "../components/common/ItemBox";
import EmptyBox from "../components/common/EmptyBox";
import RecipeBox from "../components/common/RecipeBox";

function RecipePage() {
  return (
    <div className={styles.wrapper}>
      <div className={styles.page_header}>
        <Link to="/">
          <img
            src={HomeBtn}
            className={styles.home_img}
            alt="홈버튼"
          />
        </Link>
        <span className={styles.title}>레시피</span>
      </div>
      <div className={styles.white_background}>
        <div className={styles.content}>
          <div className={styles.sub_content}>
            <p>
              나의 냉장고 속 <span>위험 단계</span> 식품들 입니다.
            </p>
            <p>
              <span>레시피를 추천</span>받아보세요.
            </p>
          </div>
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>식품 선택하기</div>
          <div className={styles.sub_content}>
            <p>레시피 속 재료가 될 식품을 선택합니다.</p>
            <section className={styles.main_section}>
              <div className={styles.section_title}>
                <h2>냉동실</h2>
              </div>
              <section style={{ border: "2px solid #ffa7a7" }}>
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
              </section>
              <div className={styles.section_title}>
                <h2>냉장실</h2>
              </div>
              <section style={{ border: "2px solid #ffd66a" }}>
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
              </section>
              <div className={styles.section_title}>
                <h2>찬장</h2>
              </div>
              <section style={{ border: "2px solid #7dd086" }}>
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
                <ItemBox
                  name="기본찬장"
                  content="D-1"
                  option="report"
                  imgSrc=""
                  onClick={() => {}}
                />
              </section>
            </section>
          </div>
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>식품 확인하기</div>
          <div className={styles.sub_content}>
            <p>선택한 재료들을 확인합니다.</p>
            <p>잘못 선택한 재료는 지워주세요.</p>
            <section className={styles.main_section}>
              <section style={{ border: "2px solid #a9a9a9" }}>
                <div className={styles.section_content}>
                  <EmptyBox />
                  <p>선택한 식품은 이 곳에서 표시됩니다.</p>
                </div>
              </section>
            </section>
          </div>
        </div>
        <div className={styles.content}>
          <div className={styles.sub_title}>레시피 선택하기</div>
          <div className={styles.sub_content}>
            <p>선택한 재료들로 만들 수 있는 레시피로 이동합니다.</p>
            <section className={styles.main_section}>
              <RecipeBox />
              <RecipeBox />
              <RecipeBox />
            </section>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RecipePage;
