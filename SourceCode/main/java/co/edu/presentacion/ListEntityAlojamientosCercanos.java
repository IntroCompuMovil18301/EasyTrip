package co.edu.presentacion;

public class ListEntityAlojamientosCercanos {
    private int imgFoto;
    private String NombreAlojamiento;
    private String calificacion;

    public ListEntityAlojamientosCercanos(int imgFoto, String nombreAlojamiento, String calificacion) {
        this.imgFoto = imgFoto;
        NombreAlojamiento = nombreAlojamiento;
        this.calificacion = calificacion;
    }

    public int getImgFoto() {
        return imgFoto;
    }

    public String getNombreAlojamiento() {
        return NombreAlojamiento;
    }

    public String getCalificacion() {
        return calificacion;
    }
}
