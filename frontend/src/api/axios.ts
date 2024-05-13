import axios, { isAxiosError } from "axios";

interface ResponseDataType {
  message?: string;
  code?: string;
}

const { VITE_API_DEV, VITE_RELOGIN_URI_BASE } = import.meta.env;

const instance = axios.create({
  baseURL: VITE_API_DEV,
  withCredentials: true,
});

instance.interceptors.response.use(
  (res) => {
    return res;
  },
  async (err) => {
    if (err.response.status === 401) {
      if (err.response.data.errorName === "UNAUTHORIZED_ACCESS") {
        try {
          await instance.post("/api/users/reissue");
          return instance(err.config);
        } catch (e) {
          if (isAxiosError<ResponseDataType>(e)) {
            localStorage.removeItem("userData");
            window.location.href = VITE_RELOGIN_URI_BASE;
          }
        }
      }
    }
    return Promise.reject(err);
  }
);

export default instance;
