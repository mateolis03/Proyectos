import { useState } from "react"
import { useEffect } from "react"
import HeaderComponent from "../../utils/Header/Header"
import SearchBarComponent from "../../utils/SearchBar/SearchBar"
import ProductComponent from "../Products/Products"

const HomeComponent = (props) => {
  const { products: productsFromAxios } = props
  const [selectedCategory, setSelectedCategory] = useState("all")
  const [productSearch, setProductSearch] = useState("")

  const bringAllProducts = () => {
    const products = []
    products.push(productsFromAxios.tech.filter(product => product.title.toUpperCase().includes(productSearch.toUpperCase()) ).map((element) => 
      renderProductElement(element)
    ))
    products.push(productsFromAxios.clothes.filter(product => product.title.toUpperCase().includes(productSearch.toUpperCase()) ).map((element) => 
      renderProductElement(element)
    ))
    products.push(productsFromAxios.luxury.filter(product => product.title.toUpperCase().includes(productSearch.toUpperCase()) ).map((element) => 
      renderProductElement(element)
    ))
    return products
  }

  const renderProductElement = (element) => {
    return(
      <ProductComponent 
      key={element.id}
      title = {element.title}
      price = {element.price}
      img = {element.img}
      qty={element.qty}
      renderedfrom = "home"
      />
    )
  }

  return( productsFromAxios &&
      <div className="home-container">
        <HeaderComponent title="Tienda de Mateo"/>
        <SearchBarComponent
        setSelectedCategory={setSelectedCategory}
        setProductSearch={setProductSearch}
        />
        <div className="products-container">
            {  selectedCategory === 'all' ? 
              bringAllProducts()
            :
            productsFromAxios[selectedCategory].filter(product => product.title.toUpperCase().includes(productSearch.toUpperCase()) ).map((element) => 
                renderProductElement(element)
              )
            }
        </div>
      </div> 
  )
}
export default HomeComponent