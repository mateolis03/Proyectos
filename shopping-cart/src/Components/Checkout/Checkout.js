import { useState, useRef } from "react"
import emailjs from '@emailjs/browser';
import HeaderComponent from "../../utils/Header/Header";
import Swal from "sweetalert2";

const CheckoutComponent = () => {
  const [email, setEmail] = useState("")
  const [cardNumber, setCardNumber] = useState("")
  const [cardHolder, setCardHolder] = useState("")
  const [expDate, setexpDate] = useState("")
  const [CVV, setCVV] = useState("")

  const sendEmail = () => {
    const emailValue = document.getElementById("email").value
    const resume = JSON.parse(localStorage.getItem("checkout_resume"))
    const items = resume.items
    const total = resume.totalCost
    const name =document.getElementById("name").value
    if(emailValue) {
      emailjs.send("service_x7m79t3","template_vv8s2dw", {
        reply_to: emailValue,
        name: "Tienda de Mateo",
        total: total,
        items: items,
        customer: name

      }, "zHEeRWqwVOm1rUhs5")
      .then((res) => {
        Swal.fire("Gracias por tu compra!","Se te envió correo de confirmación", "success")
        .then(() => {
          window.location.replace("/")
        })
      })
      .catch((e) => {
        Swal.fire("Oops!","Algo salió mal :(", "error")
        console.log(e);
      })
    } else {
      Swal.fire("Oops!","Email vacío", "error")
    }
  };

  return (
    <div className="checkout-container">
      <HeaderComponent title="Finalizar Compra"/>
      <label>Número de la tarjeta</label>
      <input type="number" nonClick={(e) => setCardNumber(e.target.value)} required/>
      <label>Fecha de expiración</label>
      <input type="text" nonClick={(e) => setexpDate(e.target.value)} required/>
      <label>CVV</label>
      <input type="number" nonClick={(e) => setCVV(e.target.value)} required/>
      <label>Nombre del propietario</label>
      <input id="name"type="text" onClick={(e) => setCardHolder(e.target.value)} required/>
      <label>Email</label>
      <input id="email" type="email" required/>
      <input type="button" onClick={() => sendEmail()} className="button" value="Pagar" />
    </div>
  )
}
export default CheckoutComponent