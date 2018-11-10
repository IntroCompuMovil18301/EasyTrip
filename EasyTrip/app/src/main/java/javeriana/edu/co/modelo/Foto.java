package javeriana.edu.co.modelo;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Foto implements Serializable{

    private String nombre;
    private String descripcion;
    private Bitmap bitmap;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
