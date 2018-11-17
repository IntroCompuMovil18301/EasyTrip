package javeriana.edu.co.modelo;

import java.io.Serializable;

public class Localizacion implements Serializable{

    private Double latitud;
    private Double longitud;
    private String ciudad;
    private String direccion;
    private String pais;
    private String subLocalidad;


    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getSubLocalidad() {
        return subLocalidad;
    }

    public void setSubLocalidad(String subLocalidad) {
        this.subLocalidad = subLocalidad;
    }
}
