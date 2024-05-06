import instance from "./axios";

export async function getDesignList() {
  try {
    const res = await instance.get("/api/refrigerators/designs");
    console.log(res);
    return res;
  } catch (e) {
    console.log(e);
  }
}

export async function getCurrentDesign() {
  try {
    const res = await instance.get("/api/refrigerators/designs/using");
    console.log(res);
    return res;
  } catch (e) {
    console.log(e);
  }
}