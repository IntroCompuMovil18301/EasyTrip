package javeriana.edu.co.modelo;

import java.sql.Date;

public class Calificacion {
    private String idReserva;
    private String idAlojamiento;
    private String nombreCalificado;
    private String nombreCalificador;
    private String comentario;
    private Double puntaje;
    private Date fecha;


    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public String getIdAlojamiento() {
        return idAlojamiento;
    }

    public void setIdAlojamiento(String idAlojamiento) {
        this.idAlojamiento = idAlojamiento;
    }

    public String getNombreCalificado() {
        return nombreCalificado;
    }

    public void setNombreCalificado(String nombreCalificado) {
        this.nombreCalificado = nombreCalificado;
    }

    public String getNombreCalificador() {
        return nombreCalificador;
    }

    public void setNombreCalificador(String nombreCalificador) {
        this.nombreCalificador = nombreCalificador;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Double puntaje) {
        this.puntaje = puntaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
