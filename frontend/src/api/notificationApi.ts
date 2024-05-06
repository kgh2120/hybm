import instance from "./axios";

export async function getIsNewNotification() {
  try {
    const res = await instance.get("/api/notices/hasnew");
    console.log(res);
    return res.status;
  } catch (e) {
    console.log(e);
  }
}

export async function deleteAllNotification() {
  try {
    const res = await instance.delete("/api/notices");
    console.log(res);
  } catch (e) {
    console.log(e);
  }
}

export async function getNotificationList() {
  try {
    const res = await instance.get("/api/notices", {
      params: {
        page: 0,
        size: 20,
      },
    });
    console.log(res);
    return res.data
  } catch (e) {
    console.log(e);
  }
}

export async function deleteNotification(noticeId: number) {
  try {
    const res = await instance.delete(`/api/notices/${noticeId}`, {
    });
    console.log(res);
    return res.data
  } catch (e) {
    console.log(e);
  }
}
