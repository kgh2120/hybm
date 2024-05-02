import axios from "axios";

const { VITE_API_DEV } = import.meta.env;

const instance = axios.create({
  baseURL: VITE_API_DEV,
  withCredentials: true,
});

export default instance;
