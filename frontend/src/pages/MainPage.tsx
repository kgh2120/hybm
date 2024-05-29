/* eslint-disable */  
import styles from "../styles/mainPage/MainPage.module.css";
import background from "../assets/images/background.png";
import recipe from "../assets/images/recipe.png";
import trashCan from "../assets/images/trashCan.png";
import tutorial from "../assets/images/tutorial.png";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useEffect, useState } from "react";
import ExpBar from "../components/common/ExpBar";
import { deleteAllFood, getBigCategoryList } from "../api/foodApi";
import { useFoodCategoryStore } from "../stores/useFoodStore";
import { getCurrentDesign, getDesignList } from "../api/fridgeApi";
import ConfirmModal from "../components/common/ConfirmModal";
import { getCurrentBadgeList } from "../api/badgeApi";
import useFridgeStore from "../stores/useFridgeStore";
import add from "../assets/images/add.png";
import minus from "../assets/images/minus.png";
import useAttachedBadgeStore, {
  useBadgeStore,
} from "../stores/useBadgeStore";
import useAuthStore from "../stores/useAuthStore";
import LoadingSpinner from "../components/common/LoadingSpinner";

interface StorageType {
  id: number;
  imgSrc: string;
}

interface CurrentDesignType {
  ice: StorageType;
  cool: StorageType;
  cabinet: StorageType;
}

interface BadgeType {
  badgeId: number;
  src: string;
  position: number;
}

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

function MainPage() {
  const location = useLocation();
  const navigate = useNavigate();
  const { setBigCategoryList } = useFoodCategoryStore();
  const { appliedDesign, setAppliedDesign, setLevelDesignList } =
    useFridgeStore();
  const { attachedBadgeList, setAttachedBadgeList } =
    useAttachedBadgeStore();
  const { selectedBadge, initSelectedBadge } = useBadgeStore();
  const [
    isDeleteAllFoodConfirmModalOpen,
    setIsDeleteAllFoodConfirmModalOpen,
  ] = useState(false);
  const { setImage } = useAuthStore();
  const { data: designList, isPending: isdesignListPending } =
    useQuery<DesignListType>({
      queryKey: ["designList"],
      queryFn: getDesignList,
    });

  useEffect(() => {
    if (designList) {
      const cabinetDesignList = designList.cabinet
        .filter((design) => design.level !== 1)
        .map((design) => {
          return {
            designImgSrc: design.designImgSrc,
            level: design.level,
            name: design.name,
          };
        });
      const coolDesignList = designList.cool
        .filter((design) => design.level !== 1)
        .map((design) => {
          return {
            designImgSrc: design.designImgSrc,
            level: design.level,
            name: design.name,
          };
        });
      const iceDesignList = designList.ice
        .filter((design) => design.level !== 1)
        .map((design) => {
          return {
            designImgSrc: design.designImgSrc,
            level: design.level,
            name: design.name,
          };
        });
      setLevelDesignList([
        ...cabinetDesignList,
        ...iceDesignList,
        ...coolDesignList,
      ]);
    }
  }, [designList]);

  const {
    data: currentDesign,
    isPending: isCurrentDesignPending,
    isError: isCurrentDesignError,
  } = useQuery<CurrentDesignType>({
    queryKey: ["currentDesign"],
    queryFn: getCurrentDesign,
  });

  const {
    data: bigCategoryList,
    isPending: isBigCategoryListPending,
    isError: isBigCategoryListError,
  } = useQuery({
    queryKey: ["bigCategoryList"],
    queryFn: getBigCategoryList,
  });

  const {
    data: currentBadgeList,
    isPending: iscurrentBadgeListPending,
    isError: iscurrentBadgeListError,
  } = useQuery<BadgeType[]>({
    queryKey: ["currentBadge"],
    queryFn: getCurrentBadgeList,
  });

  const { mutate: mutateDeleteAllFood } = useMutation({
    mutationFn: deleteAllFood,
    onSuccess: () => {
      setIsDeleteAllFoodConfirmModalOpen(false);
    },
  });

  const handleDeleteAllFood = () => {
    mutateDeleteAllFood();
  };

  const closeDeleteAllFoodConfirmModal = () => {
    setIsDeleteAllFoodConfirmModalOpen(false);
  };

  const handleOpenDeleteAllFoodConfirmModal = () => {
    setIsDeleteAllFoodConfirmModalOpen(true);
  };

  const handleOpenCamera = () => {
    // @ts-ignore
    window.flutter_inappwebview.postMessage("receipt_camera");
  };

  const sendReceipt = (image: string) => {
    setImage(image);
    navigate("/receipt");
  };

  useEffect(() => {
    // @ts-ignore
    window.sendReceipt = sendReceipt;
  }, []);

  // - 버튼을 눌렀을 때 부착한 배지 제거
  const handleDeleteAttachedBadge = (badgeId: number) => {
    const updatedAttachedBadgeList = attachedBadgeList.map(
      (badge) => {
        if (badge.badgeId !== badgeId) {
          return badge;
        } else {
          return {
            badgeId: badge.badgeId,
            position: null,
            src: badge.src,
          };
        }
      }
    );
    setAttachedBadgeList(updatedAttachedBadgeList);
  };

  const handleSelectAttachedBadge = (position: number) => {
    const existingBadgeIndex = attachedBadgeList.findIndex(
      (badge) => badge.badgeId === selectedBadge.badgeId
    );

    if (existingBadgeIndex !== -1) {
      const updatedBadgeList = [...attachedBadgeList];
      updatedBadgeList[existingBadgeIndex] = {
        badgeId: selectedBadge.badgeId,
        src: selectedBadge.src,
        position,
      };
      setAttachedBadgeList(updatedBadgeList);
    } else {
      if (selectedBadge.badgeId !== 0) {
        setAttachedBadgeList([
          ...attachedBadgeList,
          {
            badgeId: selectedBadge.badgeId,
            src: selectedBadge.src,
            position,
          },
        ]);
      }
    }
    initSelectedBadge();
  };

  useEffect(() => {
    if (bigCategoryList) {
      setBigCategoryList(bigCategoryList);
    }
  }, [bigCategoryList]);

  // 메인페이지에서 현재 적용중인 디자인 및 배지를 적용
  useEffect(() => {
    if (currentDesign) {
      setAppliedDesign({
        ice: {
          imgSrc: currentDesign.ice.imgSrc,
          designId: currentDesign.ice.id,
        },
        cool: {
          imgSrc: currentDesign.cool.imgSrc,
          designId: currentDesign.cool.id,
        },
        cabinet: {
          imgSrc: currentDesign.cabinet.imgSrc,
          designId: currentDesign.cabinet.id,
        },
      });
    }
    if (currentBadgeList) {
      setAttachedBadgeList(currentBadgeList);
    }
  }, [currentDesign, currentBadgeList]);

  if (
    isBigCategoryListPending ||
    isCurrentDesignPending ||
    iscurrentBadgeListPending ||
    isdesignListPending
  ) {
    return <LoadingSpinner />;
  }

  if (isBigCategoryListError) {
    return <div>bigCategoryList error</div>;
  } else if (isCurrentDesignError) {
    return <div>currentDesign error</div>;
  } else if (iscurrentBadgeListError) {
    return <div>currentBadge error</div>;
  }

  let content: any = [];
  if (location.pathname === "/badge") {
    content = [1, 2, 3, 4, 5, 6, 7, 8].map((position) => {
      const appliedBadge = attachedBadgeList.find(
        (badge) => badge.position === position
      );
      if (appliedBadge) {
        return (
          <div
            key={position}
            className={styles[`badge${appliedBadge.position}`]}
          >
            <div className={styles.badge_sub_box}>
              <img
                className={styles.badge}
                src={appliedBadge.src}
                alt={`배지${appliedBadge.position} 이미지`}
              />
              <img
                src={minus}
                alt="빼기 이미지"
                className={styles.minus_btn}
                onClick={() =>
                  handleDeleteAttachedBadge(appliedBadge.badgeId)
                }
              />
            </div>
          </div>
        );
      } else {
        return (
          <div
            key={position}
            className={styles[`badge${position}`]}
            onClick={() => handleSelectAttachedBadge(position)}
          >
            <div className={styles.badge_sub_box}>
              <img
                className={styles.plus_btn}
                src={add}
                alt={`배지${position} 이미지`}
              />
            </div>
          </div>
        );
      }
    });
  } else {
    content = attachedBadgeList.map((tempAppliedBadge) => {
      return (
        <img
          className={styles[`badge${tempAppliedBadge.position}`]}
          src={tempAppliedBadge.src}
          alt={`배지${tempAppliedBadge.position} 이미지`}
        />
      );
    });
  }
  return (
    <div className={styles.wrapper}>
      {location.pathname !== "/design" && location.pathname !== "/badge" && <ExpBar />}
      <Link to="/storage/cabinet">
        <img
          className={styles.cabinet}
          src={appliedDesign.cabinet.imgSrc}
          alt="찬장이미지"
        />
      </Link>
      <Link to="/recipe">
        <img
          className={styles.recipe}
          src={recipe}
          alt="레시피이미지"
        />
      </Link>
      <Link to="/storage/cool">
        <img
          className={styles.cool}
          src={appliedDesign.cool.imgSrc}
          alt="냉장이미지"
        />
      </Link>
      <Link to="/storage/ice">
        <img
          className={styles.ice}
          src={appliedDesign.ice.imgSrc}
          alt="냉동이미지"
        />
      </Link>
      <img
        className={styles.background}
        src={background}
        alt="배경이미지"
      />
      <div className={styles.receipt} onClick={handleOpenCamera}>
        영수증
      </div>
      <Link to="/design">
        <div className={styles.design}>디자인</div>
      </Link>
      <Link to="/report">
        <div className={styles.report}>보고서</div>
      </Link>
      <img
        className={styles.trash_can}
        onClick={handleOpenDeleteAllFoodConfirmModal}
        src={trashCan}
        alt="쓰레기통 이미지"
      />
      <Link to="/tutorial">
        <img
          className={styles.tutorial}
          src={tutorial}
          alt="튜토리얼 버튼"
        />
      </Link>
      {content}

      {isDeleteAllFoodConfirmModalOpen && (
        <ConfirmModal
          content="모든 식품을 삭제하시겠습니까?"
          option1="삭제"
          option2="취소"
          option1Event={handleDeleteAllFood}
          option2Event={closeDeleteAllFoodConfirmModal}
        />
      )}
    </div>
  );
}

export default MainPage;
