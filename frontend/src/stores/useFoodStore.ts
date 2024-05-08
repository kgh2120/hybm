import { create } from 'zustand';
import { persist } from 'zustand/middleware';

interface CategoryType {
  categoryId: number;
  name: string;
  categoryImgSrc: string;
}

interface BigCategoryType {
  categoryBigId: number;
  name: string;
  bigCategoryImgSrc: string;
  categoryDetails: CategoryType[];
}

interface FoodState {
  bigCategoryList: BigCategoryType[];
}

interface FoodAction {
  setBigCategoryList: (value: BigCategoryType[]) => void;
}

const useFoodStore = create(persist<FoodState & FoodAction>((set) => ({
  bigCategoryList: [],
  setBigCategoryList: (value: BigCategoryType[]) => set({bigCategoryList: value}),
}),{ name: 'foodCategoryList' }))

export default useFoodStore;