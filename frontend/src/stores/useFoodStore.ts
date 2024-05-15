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
  isSelected: boolean;
}

interface FoodAction {
  setInputList: (value: InputListType) => void;
  setIsSelected: (value: boolean) => void;
}

interface ExpiredDateType {
  year: number;
  month: number;
  day: number;
}
interface InputListType {
  foodName: string;
  categoryId: number;
  categoryBigId: number;
  categoryImgSrc: string;
  expiredDate: ExpiredDateType;
  price: number;
  location: string;
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
const initialInputList: InputListType  = {
  foodName: "",
  categoryId: 0,
  categoryBigId: 0,
  categoryImgSrc: "",
  price: 0,
  expiredDate: {
    year: new Date().getFullYear(),
    month: new Date().getMonth() + 1,
    day: new Date().getDate(),
  },
  location: "",
};
export const useFoodStore = create<FoodState & FoodAction>((set) => ({
  inputList: initialInputList,
  setInputList: (newInputList: InputListType) => set({ inputList: newInputList }),
  initialInputList,
  isSelected: false,
  setIsSelected: (value: boolean) => set({isSelected: value}),
}));

export default useFoodStore;
