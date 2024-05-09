import {
  useInfiniteQuery,
  useMutation,
} from "@tanstack/react-query";
import styles from "../../styles/mainPage/NotificationModal.module.css";
import NotificationItem from "./NotificationItem";
import {
  deleteAllNotification,
  getNotificationList,
} from "../../api/notificationApi";
import ConfirmModal from "../common/ConfirmModal";
import { useEffect, useState } from "react";
import { useInView } from "react-intersection-observer";
import EmptySection from "../common/EmptySection";

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

function NotificationModal({
  isNewNotification,
}: NotificationModalProps) {
  const { ref, inView } = useInView();
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

  const {
    data: notificationList,
    status,
    error,
    fetchNextPage,
    hasNextPage,
  } = useInfiniteQuery({
    queryKey: ["notificationList"],
    queryFn: getNotificationList,
    initialPageParam: 1,
    getNextPageParam: (lastPage, allPages) => {
      const nextPage = lastPage.length
        ? allPages.length + 1
        : undefined;
      return nextPage;
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

  useEffect(() => {
    if (inView && hasNextPage) {
      fetchNextPage();
    }
  }, [inView, hasNextPage, fetchNextPage]);

  if (status === "pending") {
    return <div>Loading...</div>;
  }
  if (status === "error") {
    return <div>Error: {error.message}</div>;
  }

  const content = notificationList?.pages.map(
    (notification: NotificationType) => {
      <NotificationItem innerRef={ref} notification={notification} />;
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
      {!isNewNotification ? <EmptySection content1="" content2=""/> : <>{content}</>}

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
