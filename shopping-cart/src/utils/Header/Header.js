import { useEffect, useState } from "react"

const HeaderComponent = (props) => {
    const {title} = props 
    const [isLoggedIn, setIsLoggedIn] = useState(false)
    useEffect(() => {
        isLoggedInFunc()
      }, [])
    
      const isLoggedInFunc = () => {
        if (typeof window !== 'undefined') {
            const auth = localStorage.getItem("auth")
            setIsLoggedIn(auth ? true : false)
        }
    }
    
    const handleLogout = () => {
        localStorage.removeItem("auth")
        window.location.reload()
    }

    return(
        <div className="header-container">
            <h1>{title}</h1>
            <nav>
                <ul>
                    <p onClick={() => {window.location.replace("/")}}>Página principal</p>
                </ul>
                <ul>
                    {
                        isLoggedIn ?
                        <p onClick={() => {window.location.replace("/my-cart")}}>Mi carrito</p>
                        :
                        <p></p>
                    }
                </ul>    
                <ul>
                    {
                        isLoggedIn ? 
                        <p onClick={() => handleLogout()}>Salir</p>
                        :
                        <p onClick={() => {window.location.replace("/login")}}>Ingresar</p>
                    }

                </ul>
            </nav>
        </div>
    )
}

export default HeaderComponent

