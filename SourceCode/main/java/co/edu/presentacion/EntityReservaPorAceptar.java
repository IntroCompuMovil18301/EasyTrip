package co.edu.presentacion;

public class EntityReservaPorAceptar {
    private int imgFotoReserva;
    private String nombreReserva;
    private String nombreCliente;
    private String calificacionCliente;

    public EntityReservaPorAceptar(int imgFotoReserva, String nombreReserva, String nombreCliente, String calificacionCliente) {
        this.imgFotoReserva = imgFotoReserva;
        this.nombreReserva = nombreReserva;
        this.nombreCliente = nombreCliente;
        this.calificacionCliente = calificacionCliente;
    }

    public int getImgFotoReserva() {
        return imgFotoReserva;
    }

    public String getNombreReserva() {
        return nombreReserva;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getCalificacionCliente() {
        return calificacionCliente;
    }
}
