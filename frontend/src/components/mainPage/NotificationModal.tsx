import { useMutation } from "@tanstack/react-query";
import styles from "../../styles/mainPage/NotificationModal.module.css";
import NotificationItem from "./NotificationItem";
import { deleteAllNotification } from "../../api/notificationApi";
import ConfirmModal from "../common/ConfirmModal";
import { useCallback, useEffect, useRef, useState } from "react";
import EmptySection from "../common/EmptySection";
import instance from "../../api/axios";

interface NotificationModalProps {
  isNewNotification: boolean;
}

interface NotificationType {
  foodId: number;
  noticeId: number;
  content: string;
  isChecked: boolean;
  foodImgSrc: string;
  createdAt: string;
}

interface NotificationListType {
  notice: NotificationType[];
  hasNext: boolean;
}

function NotificationModal({
  isNewNotification,
}: NotificationModalProps) {
  const [isLoading, setIsLoading] = useState(false);
  const [hasNext, setHasNext] = useState(false);
  const [page, setPage] = useState(1);
  const [notificationList, setNotificationList] = useState<NotificationType[]>([]);
  const observerRef = useRef();

  const lastNotificationRef = useCallback(
    (node) => {
      if (isLoading) return;
      if (observerRef.current) observerRef.current.disconnect();

      observerRef.current = new IntersectionObserver((entries) => {
        if (entries[0].isIntersecting && hasNext) {
          setPage((prevPageNumber) => prevPageNumber + 1);
        }
      });

      if (node) observerRef.current.observe(node);
    },
    [isLoading, hasNext]
  );

  useEffect(() => {
    const fetchData = async () => {
      try {
        setIsLoading(true);
        const res = await instance.get("/api/notices", {
          params: {
            page,
            size: 20,
          },
        });
        console.log("알림조회결과:", res)
        setNotificationList((prev) => [...prev, ...res.data.notice]);
        setHasNext(res.data.hasNext);
      } catch (error) {}
      setIsLoading(false);
    };
    fetchData();
  }, [page]);

  const [
    isDeleteNotificationConfirmModalOpen,
    setIsDeleteNotificationConfirmModalOpen,
  ] = useState(false);

  const { mutate: mutateDeleteAllNotification } = useMutation({
    mutationFn: deleteAllNotification,
    onSuccess: () => {
      closeDeleteNotificationConfirmModal();
    },
    onError: (error) => {
      console.error("에러났습니다.", error);
    },
  });

  const handleDeleteAllNotification = () => {
    mutateDeleteAllNotification();
  };

  const handleOpenDeleteNotificationConfirmModal = () => {
    setIsDeleteNotificationConfirmModalOpen(true);
  };

  const closeDeleteNotificationConfirmModal = () => {
    setIsDeleteNotificationConfirmModalOpen(false);
  };

  if (isLoading) {
    return <div>loading...</div>;
  }

  const content = notificationList?.map(
    (notification: NotificationType) => {
      return <NotificationItem notification={notification} />;
    }
  );

  return (
    <div className={styles.notification_modal_wrapper}>
      <button
        className={styles.remove_all_button}
        onClick={handleOpenDeleteNotificationConfirmModal}
      >
        알림함 비우기
      </button>
      {!isNewNotification ? (
        <EmptySection content1="알림함이 비었습니다." content2="" />
      ) : (
        <>{content}</>
      )}
      <div
          ref={lastNotificationRef}
          style={{ height: '5px' }}
        />

      {isDeleteNotificationConfirmModalOpen && (
        <ConfirmModal
          content="알림함을 비우시겠습니까?"
          option1="삭제"
          option2="취소"
          option1Event={handleDeleteAllNotification}
          option2Event={closeDeleteNotificationConfirmModal}
        />
      )}
    </div>
  );
}

export default NotificationModal;
