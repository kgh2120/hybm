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

interface BadgeState {
  selectedBadge: BadgeType;
}

interface BadgeAction {
  setSelectedBadge: (value: BadgeType) => void;
}

export const useBadgeStore = create<BadgeState & BadgeAction>(
  (set) => ({
    selectedBadge: {
      src: "",
      badgeId: 0,
      position: 0,
    },
    setSelectedBadge: (value: BadgeType) =>
      set({ selectedBadge: value }),
  })
);

export default useAttachedBadgeStore;
