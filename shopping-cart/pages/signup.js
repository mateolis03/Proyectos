import axios from 'axios'
import SignupComponent from '../src/Components/Signup/Signup'

export default function SignupPage(props) {
  return (
    <>
      <SignupComponent users={props.users}/>
    </>
  )
}

export async function getServerSideProps(context) {
  // Aquí se hace la petición, de esta forma el front no hace peticiones, todo queda del lado del servidor
  const url =  "https://carritodecompras-3e25c-default-rtdb.firebaseio.com/data.json"
  const users = await axios.get(url)
  
  return {
    props: {
        users: users.data.users
    }
  }
}