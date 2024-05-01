// import { useEffect } from "react";
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function AuthCallback() {    
    const navigate = useNavigate()
    // 회원정보 api 만들어지면 추가할 부분
    useEffect(() => {
        navigate("/")
    }, [])
    return (
      <></>
    )
  }
  
  export default AuthCallback