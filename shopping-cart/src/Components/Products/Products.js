import { useState, useEffect } from "react";
import Swal from "sweetalert2"

export var formatter = new Intl.NumberFormat('en-US', {
  style: 'currency',
  currency: 'COP',
});

const ProductComponent = (props) => {
  const [forceDisableButton, setForceDisableButton] = useState(false)
  const [price, setPrice] = useState(props.price)
  const [title, setTitle] = useState(props.title)
  const [img, setImg] = useState(props.img)
  const [qty, setQty] = useState(props.qty)
  
  useEffect(() => {
    const existingItem = isItemInCart()
    setForceDisableButton(existingItem)
  }, [])

  useEffect(() => {
    if(props.renderedfrom === "cart") {
      props.calculateTotalCost()
    }
  }, [qty])

  const isItemInCart = () => {
    if (typeof window !== 'undefined') {
      const cartItems = localStorage.getItem("cart") ? JSON.parse(localStorage.getItem("cart")) : []
      return cartItems.find(item => item.title === title)
    }

  }

  const handleAddToCart = () => {
    const cartItems = localStorage.getItem("cart") ? JSON.parse(localStorage.getItem("cart")) : []
    const existingItemInCart = isItemInCart()
    if(existingItemInCart) {
      cartItems.find(item => item.title === title).qty += 1
      localStorage.setItem("cart", JSON.stringify(cartItems))
      setQty(qty+1)
    } else {
      Swal.fire("Listo!", "El artículo ha sido correctamente agregado al carrito!", "success")
      cartItems.push({
        ...props,
        qty: 1
      })
      localStorage.setItem("cart", JSON.stringify(cartItems))
    }
  }

  const handleRemoveOneItemFromCart = () => {
    const cartItems = localStorage.getItem("cart") ? JSON.parse(localStorage.getItem("cart")) : []
    if(cartItems.find(item => item.title === title).qty === 1) {
      Swal.fire({
        title: 'Quieres eliminar este artículo del carrito?',
        showDenyButton: true,
        showCancelButton: true,
        confirmButtonText: 'Yes',
      }).then((result) => {
        if (result.isConfirmed) {
          const newItems = cartItems.filter(item => item.title !== title)
          localStorage.setItem("cart", JSON.stringify(newItems))
          window.location.reload()
        }
      })
    } else {
      cartItems.find(item => item.title === title).qty -= 1
      localStorage.setItem("cart", JSON.stringify(cartItems))
      setQty(qty-1)
    }
    
  }

  const renderRowButtonsQty = () => {
    return(
      <div className="row-buttons-qty">
        <p>Cantidad: </p>
        <button onClick={() => handleRemoveOneItemFromCart()}>-</button>
        <p>{qty}</p>
        <button onClick={() => handleAddToCart()}>+</button>
      </div>
    )
  }


    return(
       <div className="product-card">
           <div className="img-product">  
             <img src={img}></img>
           </div>
           <div className="info-product">
            <div className="row-title-product">             
              <p>{title}</p>
            </div>
            <div className="row-price-product">
              { props.renderedfrom === "home" ? <p>{formatter.format(price)}</p> : <p>{formatter.format(price * qty)}</p> }
              { props.renderedfrom === "cart" ? renderRowButtonsQty() : null }
            </div>

           { props.renderedfrom === "home" ? <button disabled={isItemInCart() || forceDisableButton } className="add-to-cart-button" onClick={() => {handleAddToCart(); setForceDisableButton(true)}}>Agregar al carrito</button> : null }
           </div>
       </div> 
    )
}
export default ProductComponent