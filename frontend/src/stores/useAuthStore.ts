import { create } from "zustand";
import { persist } from "zustand/middleware";

interface AuthState {
  isLogin: boolean;
  currentLevel: number;
  image: File | null;
  isCurrentNotification: boolean;
}

interface AuthAction {
  setIsLogin: (value: boolean) => void;
  setCurrentLevel: (value: number) => void;
  setImage: (value: File | null) => void;
  setIsCurrentNotification: (value: boolean) => void;
}

const useAuthStore = create(
  persist<AuthState & AuthAction>(
    (set) => ({
      isLogin: false,
      setIsLogin: (value: boolean) => set({ isLogin: value }),
      currentLevel: 1,
      setCurrentLevel: (value: number) =>
        set({ currentLevel: value }),
      image: null,
      setImage: (value: File | null) => set({ image: value }),
      isCurrentNotification: false,
      setIsCurrentNotification: (value: boolean) =>
        set({ isCurrentNotification: value }),
    }),
    { name: "userData" }
  )
);

export default useAuthStore;
