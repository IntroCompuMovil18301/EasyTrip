package co.edu.presentacion;

public class EntityCompaneros {
    private int imgFoto;
    private String nombreCompanero;
    private String calificacion;

    public EntityCompaneros(int imgFoto, String nombreCompanero, String calificacion) {
        this.imgFoto = imgFoto;
        this.nombreCompanero = nombreCompanero;
        this.calificacion = calificacion;
    }

    public int getImgFoto() {
        return imgFoto;
    }

    public String getNombreCompanero() {
        return nombreCompanero;
    }

    public String getCalificacion() {
        return calificacion;
    }
}

