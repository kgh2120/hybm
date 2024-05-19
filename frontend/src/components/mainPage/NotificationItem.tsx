import styles from "../../styles/mainPage/NotificationModal.module.css";
import { formatDate } from "../../utils/formatting";
import { deleteNotification } from "../../api/notificationApi";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { deleteFood } from "../../api/foodApi";

interface NotificationType {
  foodId: number;
  noticeId: number;
  content: string;
  isChecked: boolean;
  foodImgSrc: string;
  createdAt: string;
}

interface NotificationItemProps {
  notification: NotificationType;
  notificationList: NotificationType[];
  setNotificationList: React.Dispatch<
    React.SetStateAction<NotificationType[]>
  >;
}

function NotificationItem({
  notification,
  notificationList,
  setNotificationList,
}: NotificationItemProps) {
  const {
    foodId,
    noticeId,
    content,
    foodImgSrc,
    createdAt,
    isChecked,
  } = notification;
  const formattedDate = formatDate(createdAt);
  const queryClient = useQueryClient();
  const { mutate: mutateDeleteNotification } = useMutation({
    mutationFn: deleteNotification,
    onSuccess: () => {
      const updatedNotificationList = notificationList.filter(
        (notification) => notification.noticeId !== noticeId
      );
      setNotificationList(updatedNotificationList);
    },
    onError: (error) => {
      console.error("에러났습니다.", error);
    },
  });

  const { mutate: mutateDeleteFood } = useMutation({
    mutationFn: deleteFood,
    onSuccess: () => {
      const updatedNotificationList = notificationList.map(
        (notification) => {
          return notification.noticeId === noticeId
            ? { ...notification, isChecked: true }
            : notification;
        }
      );
      queryClient.invalidateQueries({
        queryKey: ["levelAndExp"],
      });
      setNotificationList(updatedNotificationList);
    },
  });

  const handleDeleteNotification = () => {
    mutateDeleteNotification(noticeId);
  };

  const handleDeleteFood = (option: string) => {
    mutateDeleteFood({ foodIdList: [foodId], option });
  };

  const notificationItemStyles = isChecked ? "notification_item" : "notification_gray_item";
  const buttonStyles = isChecked ? "is_disabled" : "is_abled";
  return (
    <div className={styles[notificationItemStyles]}>
      <div className={styles.main_section}>
        <div className={styles.image_section}>
          <img src={foodImgSrc} alt="음식 이미지" />
        </div>
        <div className={styles.content_section}>
          <span>{content}</span>
          <div>
            <span>{formattedDate}</span>
            <div>
              <button onClick={() => handleDeleteFood("eaten")} className={styles[buttonStyles]}>
                먹었어요
              </button>
              <button onClick={() => handleDeleteFood("thrown")} className={styles[buttonStyles]}>
                버렸어요
              </button>
            </div>
          </div>
        </div>
        <div
          className={styles.button_section}
          onClick={handleDeleteNotification}
        >
          x
        </div>
      </div>
    </div>
  );
}

export default NotificationItem;
