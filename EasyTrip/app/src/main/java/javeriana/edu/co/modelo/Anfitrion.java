package javeriana.edu.co.modelo;

import java.io.Serializable;

public class Anfitrion implements Serializable{

    private String nomUsuario;
    private String nombre;
    private String URL_Foto;
    private String email;

    public Anfitrion(){}


    public Anfitrion(String nomUsuario,String nombre,String URL_Foto, String em) {
        this.nomUsuario = nomUsuario;
        this.nombre = nombre;
        this.URL_Foto = URL_Foto;
        this.email = em;
    }

    public String getNombre() {
        return nombre;
    }

    public String getURL_Foto() {
        return URL_Foto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setURL_Foto(String URL_Foto) {
        this.URL_Foto = URL_Foto;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public boolean agregarAlojamiento(String tipo, double costo , int numHuespeds, String descripcion, String URL_foto){
        return true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
