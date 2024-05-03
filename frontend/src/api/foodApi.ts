import instance from './axios';

export async function getFoodCategoryList() {
  try {
    // const res = await instance.get('/foods/category', {});
    const res = await instance.get('/foods/category');
    console.log(res);
    return res.data;
  } catch (e) {
    console.log(e);
  }
}