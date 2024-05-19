import styles from "../../styles/mainPage/NotificationModal.module.css";
import NotificationItem from "./NotificationItem";
import { useCallback, useEffect, useRef, useState } from "react";
import EmptySection from "../common/EmptySection";
import instance from "../../api/axios";
import LoadingSpinner from "../common/LoadingSpinner";

interface NotificationType {
  foodId: number;
  noticeId: number;
  content: string;
  isChecked: boolean;
  foodImgSrc: string;
  createdAt: string;
}

function NotificationModal() {
  const [isLoading, setIsLoading] = useState(false);
  const [hasNext, setHasNext] = useState(false);
  const [page, setPage] = useState(1);
  const [notificationList, setNotificationList] = useState<
    NotificationType[]
  >([]);
  const observerRef = useRef<IntersectionObserver | null>(null);

  const lastNotificationRef = useCallback(
    (node: Element | null) => {
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
        setNotificationList((prev) => [...prev, ...res.data.notice]);
        setHasNext(res.data.hasNext);
      } catch (error) {
        console.error(error);
      }
      setIsLoading(false);
    };
    fetchData();
  }, [page]);

  const content = notificationList?.map(
    (notification: NotificationType) => {
      return (
        <NotificationItem
          notification={notification}
          setNotificationList={setNotificationList}
          notificationList={notificationList}
        />
      );
    }
  );

  return (
    <div className={styles.notification_modal_wrapper}>
      {notificationList.length === 0 ? (
        <EmptySection content1="알림함이 비었습니다." content2="" />
      ) : (
        <>
          {content}
          <div ref={lastNotificationRef} style={{ height: "5px" }} />
        </>
      )}
    </div>
  );
}

export default NotificationModal;
