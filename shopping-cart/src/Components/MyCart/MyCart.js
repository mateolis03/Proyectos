import { useEffect, useState } from "react"
import HeaderComponent from "../../utils/Header/Header"

import ProductComponent, { formatter } from "../Products/Products"

const MyCartComponent = () => {
  const [cartItems, setCartItems] = useState('')
  const [totalCost, setTotalCost] = useState('')

  useEffect(() => {
    calculateTotalCost()
  }, [])

  const calculateTotalCost = () => {
    const items = JSON.parse(localStorage.getItem("cart"))
    let auxilarTotalCost = 0
    setCartItems(items)
    items.forEach((element) => {
      auxilarTotalCost += element.price * element.qty
    })
    if (auxilarTotalCost<=100000 && auxilarTotalCost!=0){
      auxilarTotalCost+=10000;
    }
    setTotalCost(auxilarTotalCost)
  }

  const handleCheckout = () => {
    const items = JSON.parse(localStorage.getItem("cart"))

    const checkoutResume = {
      items: ``,
      totalCost: totalCost
    }
    items.forEach((element) => {
      checkoutResume.items += `${element.title} (x${element.qty}) -> ${element.price}
      \n
      ---------------------
      `
    })
    localStorage.setItem("checkout_resume", JSON.stringify(checkoutResume))

    window.location.replace("/checkout")
  }

  const renderCheckoutInfo = () => {
    return(
      <div className="cont-checkout-info">
        <p className="total-text">Total: { formatter.format(totalCost)}</p>
       <ul>
        {
          totalCost>=100000 && totalCost!==0 ?
          
        <h3>El envío es gratis 😋</h3>
        :
        <h3>El envío tiene un costo de COP 10,000.00</h3>
        
        }
        </ul>
        <button onClick={() => handleCheckout()} className="button-checkout">Pagar</button>
        
      </div>
    )
  }


  return( cartItems !== "" &&
      <div className="my-cart-container">
        <HeaderComponent title="My cart" />
         { cartItems ?
         <div className="products-container">
          {
            cartItems.map((element) => 
                <ProductComponent 
                calculateTotalCost={calculateTotalCost}
                key={element.id}
                title = {element.title}
                price = {element.price}
                img = {element.img}
                renderedfrom = "cart"
                qty = {element.qty}
                />
              )
            }
         
         </div>
            :
            <p>No hay productos en tu carrito :(</p>
          }
          {cartItems && renderCheckoutInfo()}
          
      </div> 
  )
}
export default MyCartComponent