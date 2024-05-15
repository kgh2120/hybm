import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface AuthState {
  isLogin: boolean;
  currentLevel: number;
  image: File | null;
}

interface AuthAction {
  setIsLogin: (value: boolean) => void;
  setCurrentLevel: (value: number) => void;
  setImage: (value: File) => void;
}

const useAuthStore = create(
  persist<AuthState & AuthAction>(
  (set) => ({
  isLogin: false,
  setIsLogin: (value: boolean) => set({isLogin: value}),
  currentLevel: 1,
  setCurrentLevel: (value: number) => set({currentLevel: value}),
  image: null,
  setImage: (value: File) => set({image: value}),
}),{ name: 'userData' }))

export default useAuthStore;