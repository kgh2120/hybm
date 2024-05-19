import { create } from "zustand";
import { persist } from "zustand/middleware";

interface BadgeType {
  src: string;
  badgeId: number;
  position: number | null;
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
  initSelectedBadge: () => void;
}

export const useBadgeStore = create<BadgeState & BadgeAction>(
  (set) => ({
    selectedBadge: {
      src: "",
      badgeId: 0,
      position: null,
    },
    setSelectedBadge: (value: BadgeType) =>
      set({ selectedBadge: value }),
    initSelectedBadge: () =>
      set({ selectedBadge: {src: "", badgeId: 0, position: null} }),
  })
);

export default useAttachedBadgeStore;
