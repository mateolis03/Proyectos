import axios from 'axios'
import HomeComponent from '../src/Components/Home/Home'

export default function Home(props) {
  
  return (
    <>
      <HomeComponent products={props.products}/>
    </>
  )
}

export async function getServerSideProps(context) {
  const url = "https://carritodecompras-3e25c-default-rtdb.firebaseio.com/data.json"
  const products = await axios.get(url)
  
  return {
    props: {
      products: products.data.products
    }
  }
}