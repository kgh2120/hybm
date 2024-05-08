import axios from "axios";

const { VITE_API_DEV } = import.meta.env;

const instance = axios.create({
  baseURL: VITE_API_DEV,
  withCredentials: true,
});

// instance.interceptors.request.use(
//   (config) => {
//     const userDataString = localStorage.getItem('userData');
//     if (userDataString !== null) {
//       const userData = JSON.parse(userDataString);
//       const accessToken = userData.state.accessToken;
//       console.log('accesstoken: ', accessToken);
//       config.headers['Authorization'] = accessToken;
//     } else {
//       console.log('userData가 null입니다.');
//     }

//     return config;
//   },
//   (error) => {
//     console.log('config 에러: ', error);
//     return Promise.reject(error);
//   }
// );

export default instance;
