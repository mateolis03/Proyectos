import { useEffect } from 'react';
import CheckoutComponent from '../src/Components/Checkout/Checkout';

export default function Home(props) {
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
      <CheckoutComponent />
    </>
  )
}
