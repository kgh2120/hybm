import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface AuthState {
  isLogin: boolean;
  currentLevel: number;
}

interface AuthAction {
  setIsLogin: (value: boolean) => void;
  setCurrentLevel: (value: number) => void;
}

const useAuthStore = create(
  persist<AuthState & AuthAction>(
  (set) => ({
  isLogin: false,
  setIsLogin: (value: boolean) => set({isLogin: value}),
  currentLevel: 1,
  setCurrentLevel: (value: number) => set({currentLevel: value}),
}),{ name: 'userData' }))

export default useAuthStore;