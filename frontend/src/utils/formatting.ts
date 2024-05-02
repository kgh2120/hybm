export const formatPrice = (price: number | string) => {
  return price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','); // 3자리마다 쉼표 추가
};