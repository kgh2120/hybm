import { create } from "zustand";
import { persist } from "zustand/middleware";

interface DesignType {
  imgSrc: string;
  designId: number;
}

interface AppliedDesignType {
  ice: DesignType;
  cool: DesignType;
  cabinet: DesignType;
}
interface DesignState {
  appliedDesign: AppliedDesignType;
}

interface DesignAction {
  setAppliedDesign: (value: AppliedDesignType) => void;
}

const useFridgeStore = create(
  persist<DesignState & DesignAction>(
    (set) => ({
      appliedDesign: {
        ice: { imgSrc: "", designId: 0 },
        cool: { imgSrc: "", designId: 0 },
        cabinet: { imgSrc: "", designId: 0 },
      },
      setAppliedDesign: (value: AppliedDesignType) =>
        set({ appliedDesign: value }),
    }),
    { name: "fridgeData" }
  )
);

export default useFridgeStore;
