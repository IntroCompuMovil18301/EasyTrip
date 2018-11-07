package javeriana.edu.co.modelo;

import java.io.Serializable;

public class Huesped implements Serializable{
    private String nomUsuario;
    private String nombre;
    private String URL_Foto;
    private String genero;
    private String nacionalidad;
    private String tipo_Viaje;
    private String fumador;
    private String sobreMi;

    public Huesped(String nomUsuario,String nombre, String URL_Foto, String genero, String nacionalidad, String tipo_Viaje, boolean fumador, String sobreMi){
        this.nomUsuario = nomUsuario;
        this.nombre = nombre;
        this.URL_Foto = URL_Foto;
        this.genero = genero;
        this.nacionalidad = nacionalidad;
        this.tipo_Viaje = tipo_Viaje;
        this.fumador = (fumador)? "Si" : "No";
        this.sobreMi = sobreMi;
    }

    public String getGenero() {
        return genero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public String getTipo_Viaje() {
        return tipo_Viaje;
    }

    public String getSobreMi(){
        return sobreMi;
    }

    public String isFumador() {
        return fumador;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public void setTipo_Viaje(String tipo_Viaje) {
        this.tipo_Viaje = tipo_Viaje;
    }

    public void setFumador(String fumador) {
        this.fumador = fumador;
    }

    public void setSobreMi(String sobreMi){
        this.sobreMi = sobreMi;
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
}
