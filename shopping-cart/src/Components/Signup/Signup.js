import { useState } from "react"
import jwt from 'jwt-simple'
import axios from "axios"
import Swal from "sweetalert2"
const SignupComponent = (props) => {
  const [username, setusername] = useState("all")
  const [password, setPassword] = useState("")

  const handleRegister = () => {
    if(username !== "" && password !== "") {
      const url =  "https://carritodecompras-3e25c-default-rtdb.firebaseio.com/data.json"
      const payload = {
        "products": {
          "tech": [{
                  "id": 1,
                  "title": "iPhone 13",
                  "price": 100000,
                  "img": "https://i.ibb.co/177ZWdG/iphone-13.png"
              },
              {
                  "id": 2,
                  "title": "Macbook Pro",
                  "price": 200000,
                  "img": "https://i.ibb.co/p4R4Tcv/macbook.jpg"
              }
          ],
          "clothes": [{
                  "id": 3,
                  "title": "Tennis Nike",
                  "price": 25000,
                  "img": "https://i.ibb.co/xMGJ6Mh/airforce.jpg"
              },
              {
                  "id": 4,
                  "title": "Zapatos Versace",
                  "price": 30000,
                  "img": "https://i.ibb.co/wyTBtCQ/zapatos-Versace.webp"
              },
              {
                  "id": 5,
                  "title": "Saco Adidas",
                  "price": 45000,
                  "img": "https://i.ibb.co/LnqvvSW/adidashoodie.webp"
              }
          ],
          "luxury": [{
                  "id": 6,
                  "title": "Gafas Versace",
                  "price": 80000,
                  "img": "https://i.ibb.co/gmK61Bz/gafas-versace.webp"
              },
              {
                  "id": 7,
                  "title": "Gafas Gucci",
                  "price": 120000,
                  "img": "https://i.ibb.co/8mcb7d9/gucci-gafas.jpg"
              },
              {
                  "id": 8,
                  "title": "Gafas Armani",
                  "price": 75000,
                  "img": "https://i.ibb.co/2h587Zz/armani-gafas.jpg"
              },
              {
                  "id": 9,
                  "title": "Camiseta Dior",
                  "price": 65000,
                  "img": "https://i.ibb.co/cLRjPYb/camiseta-Dior.webp"
              },
              {
                  "id": 10,
                  "title": "Reloj Rolex",
                  "price": 500000,
                  "img": "https://i.ibb.co/1KkYL19/rolex.webp"
              }
          ]
      },
      "users": [
        {
          "username": username,
          "password": password
      },  
      {
              "username": "luisfelipe1",
              "password": "admin123"
          },
          {
              "username": "mateolis03",
              "password": "mateo1"
          },
      ]
      }

      axios.put(url, payload)
      .then(() => {
        Swal.fire("Bien hecho!!","Has sido registrado, te enviaremos a la página de ingreso :)", "success")
        .then(() => {
          var secret = 'shhhhhh';
          const token = jwt.encode(payload, secret)
          localStorage.setItem("auth", token)
          window.location.href = "/login"
        })

      })
      .catch(() => {

      })

    } else  {
      Swal.fire("Oops!","Por favor completa los campos requeridos :(", "warning")
    }

  }
  return(  
      <div className="login-container">
        <h1>Registro </h1>
        <input placeholder="username" type="input" onChange={(e) => setusername(e.target.value)} />
        <input placeholder="Password" type="password" onChange={(e) => setPassword(e.target.value)}/>
        <button onClick={() => handleRegister()} >Registrarse</button>
        <p></p>
        <a href="/login">¿Ya tienes una cuenta?</a>
      </div>
      
    
  )
}
export default SignupComponent