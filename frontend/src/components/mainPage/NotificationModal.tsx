import { useMutation, useQuery } from "@tanstack/react-query";
import styles from "../../styles/mainPage/NotificationModal.module.css";
import NotificationItem from "./NotificationItem";
import {
  deleteAllNotification,
  getNotificationList,
} from "../../api/notificationApi";
import ConfirmModal from "../common/ConfirmModal";
import { useState } from "react";

interface NotificationModalProps {
  isNewNotification: boolean;
}

function NotificationModal({ isNewNotification }: NotificationModalProps) {
  const [
    isDeleteNotificationConfirmModalOpen,
    setIsDeleteNotificationConfirmModalOpen,
  ] = useState(false);

  const { mutate: mutateDeleteAllNotification } = useMutation({
    mutationFn: deleteAllNotification,
    onSuccess: () => {
      // navigate('/group');
      // closeRewardModal()
    },
    onError: (error) => {
      console.error("에러났습니다.", error);
    },
  });

  const {
    data: notificationList,
    isPending: isNotificationListPending,
    isError: isNotificationListError,
  } = useQuery({
    queryKey: ["notificationList"],
    queryFn: getNotificationList,
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

  if (isNotificationListPending) {
    return <div>notificationList Loading...</div>;
  }
  if (isNotificationListError) {
    return <div>notificationList Error...</div>;
  }
  console.log(isNewNotification)
  return (
    <div className={styles.notification_modal_wrapper}>
      <button
        className={styles.remove_all_button}
        onClick={handleOpenDeleteNotificationConfirmModal}
      >
        알림함 비우기
      </button>
      {notificationList.notice.map((notification: any) => {
        <NotificationItem
          foodId={notification.foodId}
          noticeId={notification.noticeId}
          content={notification.content}
          isChecked={notification.isChecked}
          foodImgSrc={notification.foodImgSrc}
          createdAt={notification.createdAt}
        />;
      })}
      {isDeleteNotificationConfirmModalOpen && (
        <ConfirmModal
          content="정말로 삭제하시겠습니까?"
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
