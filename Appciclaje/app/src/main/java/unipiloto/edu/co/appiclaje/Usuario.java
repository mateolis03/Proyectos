package unipiloto.edu.co.appiclaje;

public class Usuario {
    String nombre_completo;
    String email;
    String password;
    String telefono;
    String direccion;


    public Usuario(String nombre_completo, String email, String password, String telefono, String direccion) {
        this.nombre_completo = nombre_completo;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre_completo='" + nombre_completo + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
