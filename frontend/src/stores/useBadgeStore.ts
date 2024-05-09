import { create } from "zustand";
import { persist } from "zustand/middleware";

interface BadgeType {
  src: string;
  badgeId: number;
  position: number;
}

interface AttachedBadgeState {
  attachedBadgeList: BadgeType[];
}

interface AttachedBadgeAction {
  setAttachedBadgeList: (value: BadgeType[]) => void;
}

const useAttachedBadgeStore = create(
  persist<AttachedBadgeState & AttachedBadgeAction>(
    (set) => ({
      attachedBadgeList: [],
      setAttachedBadgeList: (value: BadgeType[]) =>
        set({ attachedBadgeList: value }),
    }),
    { name: "attachedBadgeList" }
  )
);

export default useAttachedBadgeStore;
