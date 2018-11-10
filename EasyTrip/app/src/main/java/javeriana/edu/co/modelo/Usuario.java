package Modelo;

import java.io.Serializable;

public class Usuario implements Serializable{
    private String nomUsuario;
    private String contrasena;
    private String email;
    private String tipo;

    public Usuario(){

    }

    public Usuario(String nomUsuario, String contrasena, String email, String tipo) {
        this.nomUsuario = nomUsuario;
        this.contrasena = contrasena;
        this.email = email;
        this.tipo = tipo;
    }

    public String getNomUsuario() {
        return nomUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNomUsuario(String nomUsuario) {
        this.nomUsuario = nomUsuario;
    }

    public void setContrasena(String contraseña) {
        this.contrasena = contraseña;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }


}
