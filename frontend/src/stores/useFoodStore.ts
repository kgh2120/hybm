import { create } from "zustand";
import { persist } from "zustand/middleware";

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

interface FoodCategoryState {
  bigCategoryList: BigCategoryType[];
}

interface FoodCategoryAction {
  setBigCategoryList: (value: BigCategoryType[]) => void;
}
interface FoodState {
  inputList: InputListType;
  initialInputList: InputListType;
}

interface FoodAction {
  setInputList: (value: InputListType) => void;
}

interface ExpiredDateType {
  year: number;
  month: number;
  day: number;
}
interface InputListType {
  foodName: string;
  categoryId: number;
  expiredDate: ExpiredDateType;
  price: number;
}

export const useFoodCategoryStore = create(
  persist<FoodCategoryState & FoodCategoryAction>(
    (set) => ({
      bigCategoryList: [],
      setBigCategoryList: (value: BigCategoryType[]) =>
        set({ bigCategoryList: value }),
    }),
    { name: "foodCategoryList" }
  )
);

export const useFoodStore = create<FoodState & FoodAction>((set) => ({
  inputList: {
    foodName: "",
    categoryId: 0,
    expiredDate: {
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
      day: new Date().getDate(),
    },
    price: 0,
  },
  setInputList: (value: InputListType) => set({ inputList: value }),
  initialInputList: {
    foodName: "",
    categoryId: 0,
    expiredDate: {
      year: new Date().getFullYear(),
      month: new Date().getMonth() + 1,
      day: new Date().getDate(),
    },
    price: 0,
  },
}));

export default useFoodStore;