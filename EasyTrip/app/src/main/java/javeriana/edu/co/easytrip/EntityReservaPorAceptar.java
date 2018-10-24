package javeriana.edu.co.easytrip;

public class EntityReservaPorAceptar {
    private int imgFotoReserva;
    private String nombreReserva;
    private String fechaInicio;
    private String fechaFin;

    public EntityReservaPorAceptar(int imgFotoReserva, String nombreReserva, String fechaInicio, String fechaFin) {
        this.imgFotoReserva = imgFotoReserva;
        this.nombreReserva = nombreReserva;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getImgFotoReserva() {
        return imgFotoReserva;
    }

    public String getNombreReserva() {
        return nombreReserva;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }
}