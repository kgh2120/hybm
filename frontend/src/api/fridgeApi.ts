import instance from "./axios";

// 냉장고 디자인 전체 조회
const getDesignList = async () => {
  try {
    const res = await instance.get("/api/refrigerators/designs");
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

// 적용한 디자인 조회(냉동/냉장/선반)
const getCurrentDesign = async () => {
  try {
    const res = await instance.get(
      "/api/refrigerators/designs/using"
    );
    return res.data;
  } catch (e) {
    console.log(e);
  }
};

interface PutDesignProps {
  iceDesignId: number;
  coolDesignId: number;
  cabinetDesignId: number;
}

// 냉장고 디자인 변경
const putDesign = async ({
  iceDesignId,
  coolDesignId,
  cabinetDesignId,
}: PutDesignProps) => {
  const data = {
    request: [
      { position: "ICE", designId: iceDesignId },
      { position: "COOL", designId: coolDesignId },
      { position: "CABINET", designId: cabinetDesignId },
    ],
  };
  try {
    const res = await instance.put(
      "/api/refrigerators/designs",
      data
    );
    return res;
  } catch (e) {
    console.log(e);
    throw e;
  }
};

export { getDesignList, getCurrentDesign, putDesign };
