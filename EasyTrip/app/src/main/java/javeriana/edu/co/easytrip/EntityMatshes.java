package javeriana.edu.co.easytrip;

public class EntityMatshes {
    private int imgFotoMatsh;
    private String nombreMatsh;
    private String fechaInicio;
    private String fechaFin;

    public EntityMatshes(int imgFotoMatsh, String nombreMatsh, String fechaInicio, String fechaFin) {
        this.imgFotoMatsh = imgFotoMatsh;
        this.nombreMatsh = nombreMatsh;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getImgFotoMatsh() {
        return imgFotoMatsh;
    }

    public String getNombreMatsh() {
        return nombreMatsh;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }
}