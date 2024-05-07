import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface Category {
  categoryId: number;
  name: string;
  categoryImgSrc: string;
}

interface BigCategory {
  categoryBigId: number;
  name: string;
  bigCategoryImgSrc: string;
  categoryDetails: Category[];
}

interface FoodState {
  bigCategoryList: BigCategory[];
}

interface FoodAction {
  setBigCategoryList: (value: BigCategory[]) => void;
}

const useFoodStore = create(persist<FoodState & FoodAction>((set) => ({
  bigCategoryList: [],
  setBigCategoryList: (value: BigCategory[]) => set({bigCategoryList: value}),
}),{ name: 'foodCategoryList' }))

export default useFoodStore;