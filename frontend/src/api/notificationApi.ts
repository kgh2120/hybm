import instance from "./axios";

// 미확인 알림 여부 조회(빨간점)
const getIsNewNotification = async () => {
  try {
    const res = await instance.get("/api/notices/hasnew");
    return res.data.hasNew;
  } catch (e) {
    console.log(e);
  }
};

// 알텅텅(전부삭제)
const deleteAllNotification = async () => {
  try {
    const res = await instance.delete("/api/notices/all");
    console.log(res);
  } catch (e) {
    console.log(e);
  }
};

// 알림 전체 조회(확인한 것도)
const getNotificationList = async () => {
  try {
    const res = await instance.get("/api/notices", {
      params: {
        page: 0,
        size: 20,
      },
    });
    console.log("notice:", res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

// 알림 삭제
const deleteNotification = async (noticeId: number) => {
  try {
    const res = await instance.delete(`/api/notices/${noticeId}`, {});
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

export {
  getIsNewNotification,
  deleteAllNotification,
  getNotificationList,
  deleteNotification,
};
