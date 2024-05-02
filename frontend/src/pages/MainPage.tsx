import { Link } from 'react-router-dom'
import levelBar from '../assets/levelBar.png'

function MainPage() {
  return (
    <div>
      <Link to='/'><img src={levelBar} alt="" /></Link>
      <Link to='/badge'>배지</Link>
      <Link to='/design'>디자인</Link>
      <Link to='/landing'>랜딩</Link>
      <Link to='/storage/cabinet'>찬장</Link>
      <Link to='/storage/cool'>냉장</Link>
      <Link to='/storage/ice'>냉동</Link>
      <Link to='/report'>보고서</Link>
    </div>
  )
}

export default MainPage