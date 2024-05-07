import instance from "./axios";

const getDesignList = async () => {
  try {
    const res = await instance.get("/api/refrigerators/designs");
    console.log(res);
    return res;
  } catch (e) {
    console.log(e);
  }
}

const getCurrentDesign = async () => {
  try {
    const res = await instance.get("/api/refrigerators/designs/using");
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}

export { getDesignList, getCurrentDesign }