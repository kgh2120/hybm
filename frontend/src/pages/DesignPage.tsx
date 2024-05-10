import MainPage from "./MainPage";
import styles from "../styles/designPage/DesignPage.module.css";
import WhiteSection from "../components/common/WhiteSection";
import Button from "../components/common/Button";
import leftArrow from "../assets/images/leftArrow.png";
import { Link, useNavigate } from "react-router-dom";
import { getDesignList, putDesign } from "../api/fridgeApi";
import { useMutation, useQuery } from "@tanstack/react-query";
import useFridgeStore from "../stores/useFridgeStore";

interface DesignType {
  designImgSrc: string;
  has: boolean;
  isApplied: boolean;
  level: number;
  location: string;
  name: string;
  storageDesignId: number;
}

interface DesignListType {
  cabinet: DesignType[];
  cool: DesignType[];
  ice: DesignType[];
}

function DesignPage() {
  const navigate = useNavigate();
  const { appliedDesign } = useFridgeStore();
  const {
    data: designList,
    isPending: isdesignListPending,
    isError: isdesignListError,
  } = useQuery<DesignListType>({
    queryKey: ["designList"],
    queryFn: getDesignList,
  });

  const { mutate: mutatePutDesign } = useMutation({
    mutationFn: putDesign,
    onSuccess: () => {
      navigate("/");
    },
    onError: (error) => {
      console.error("에러났습니다.", error);
    },
  });

  const handlePutDesign = () => {
    const iceDesignId = appliedDesign.ice.designId;
    const coolDesignId = appliedDesign.cool.designId;
    const cabinetDesignId = appliedDesign.cabinet.designId;
    mutatePutDesign({ iceDesignId, coolDesignId, cabinetDesignId });
  };

  if (isdesignListPending) {
    return <div>designList Loding...</div>;
  }
  if (isdesignListError) {
    return <div>designList error</div>;
  }

  return (
    <div className={styles.wrapper}>
      <div className={styles.background}>
        <MainPage />
      </div>
      <section className={styles.white_section}>
        <WhiteSection title="찬장" designList={designList.cabinet} />
        <WhiteSection title="냉장" designList={designList.cool} />
        <WhiteSection title="냉동" designList={designList.ice} />
        <div className={styles.button_box}>
          <Button
            content="적용"
            color="red"
            onClick={handlePutDesign}
            disabled={false}
          />
          <Link to="/">
            <Button
              content="홈으로"
              color="white"
              onClick={() => {}}
              disabled={false}
            />
          </Link>
        </div>
      </section>
      <Link to="/badge">
        <img
          className={styles.left_arrow}
          src={leftArrow}
          alt="왼쪽화살표 이미지"
        />
      </Link>
    </div>
  );
}

export default DesignPage;
