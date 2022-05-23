 import { useState } from "react"
import jwt from 'jwt-simple'
import Swal from "sweetalert2";

const LoginComponent = (props) => {
  const { users } = props
  const [username, setusername] = useState("all")
  const [password, setPassword] = useState("")

  const handleAuth = () => {
    if(username !== "" && password !== "") {
      console.log(users);
      users && users.map((user, iteration) => {
        if(username === user.username && password === user.password) {
          var secret = 'shhhhhh';
          const token = jwt.encode(user, secret)

          localStorage.setItem("auth", token)
          window.location.href = "/"
          
        } else if((iteration === users.length) &&( username !== user.username || password !== user.password)) {
          Swal.fire("Oops!","Usuario o contraseña incorrecta :(", "error")
        }
      })
    } else  {
      Swal.fire("Oops!","Por favor completa los campos requeridos :(", "error")
    }

  }
  return(  
      <div className="login-container">
        <h1>Tienda de Mateo</h1>
        <input placeholder="Ingrese su usuario" type="input" onChange={(e) => setusername(e.target.value)} />
        <input placeholder="Ingrese su contraseña" type="password" onChange={(e) => setPassword(e.target.value)}/>
        <button onClick={() => handleAuth()} >Ingresar</button>
        <p></p>
        <a href="/signup">Registrate!</a>
      </div>
    
  )
}
export default LoginComponent