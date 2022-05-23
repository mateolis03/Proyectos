import { useEffect } from 'react';
import MyCartComponent from '../src/Components/MyCart/MyCart';

export default function MyCartPage(props) {
  console.log(props.products, "FSDFSS");
  useEffect(() => {
    isLogedIn()
  }, [])


  const isLogedIn = () => {
    const auth = localStorage.getItem("auth")
    if(!auth) {
      window.location.href = "/login"
    }
  }

  return ( 
    <>
      <MyCartComponent />
    </>
  )
}
