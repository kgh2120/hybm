import { create } from "zustand";
import { persist } from "zustand/middleware";

interface AuthState {
  isLogin: boolean;
  currentLevel: number | null;
  image: string | null;
  isCurrentNotification: boolean;
}

interface AuthAction {
  setIsLogin: (value: boolean) => void;
  setCurrentLevel: (value: number) => void;
  setImage: (value: string | null) => void;
  setIsCurrentNotification: (value: boolean) => void;
}

const useAuthStore = create(
  persist<AuthState & AuthAction>(
    (set) => ({
      isLogin: false,
      setIsLogin: (value: boolean) => set({ isLogin: value }),
      currentLevel: null,
      setCurrentLevel: (value: number) =>
        set({ currentLevel: value }),
      image: null,
      setImage: (value: string | null) => set({ image: value }),
      isCurrentNotification: false,
      setIsCurrentNotification: (value: boolean) =>
        set({ isCurrentNotification: value }),
    }),
    { name: "userData" }
  )
);

export default useAuthStore;
