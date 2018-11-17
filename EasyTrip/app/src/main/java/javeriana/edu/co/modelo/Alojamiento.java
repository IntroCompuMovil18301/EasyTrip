package javeriana.edu.co.modelo;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Alojamiento implements Serializable{


    private String id;
    private String idUsuario;
    private String nombre;
    private String tipo;
    private Double costo;
    private String descripcion;
    private Double valoracion;
    private int maxHuespedes;
    private int numHabitaciones;
    private String bano;
    private boolean ducha;
    private boolean cocina;
    private boolean salonEventos;
    private Double valorEventos;
    private boolean discapacitados;
    private boolean wifi;


    private boolean serHabitacion;
    private boolean serParqueadero;
    private boolean cafeBar;
    private boolean spa;
    private boolean gimnasio;

    private boolean piscina;
    private boolean jacuzzi;
    private boolean banoTurco;
    private boolean tina;
    private boolean sauna;
    private boolean salaNinos;
    private boolean refrigerador;
    private boolean microhondas;
    private boolean television;
    private boolean equipoSonido;
    private boolean cajaFuerte;
    private boolean teatro;

    private Localizacion localiza;


    public String getServicios(){
        return "Servicios...";
    }

    public String getElectrodomesticos(){
        return "Electro...";
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getValoracion() {
        return valoracion;
    }

    public void setValoracion(Double valoracion) {
        this.valoracion = valoracion;
    }

    public int getMaxHuespedes() {
        return maxHuespedes;
    }

    public void setMaxHuespedes(int maxHuespedes) {
        this.maxHuespedes = maxHuespedes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getNumHabitaciones() {
        return numHabitaciones;
    }

    public void setNumHabitaciones(int numHabitaciones) {
        this.numHabitaciones = numHabitaciones;
    }

    public String getBano() {
        return bano;
    }

    public void setBano(String bano) {
        this.bano = bano;
    }

    public boolean isDucha() {
        return ducha;
    }

    public void setDucha(boolean ducha) {
        this.ducha = ducha;
    }

    public boolean isCocina() {
        return cocina;
    }

    public void setCocina(boolean cocina) {
        this.cocina = cocina;
    }

    public boolean isSalonEventos() {
        return salonEventos;
    }

    public void setSalonEventos(boolean salonEventos) {
        this.salonEventos = salonEventos;
    }

    public Double getValorEventos() {
        return valorEventos;
    }

    public void setValorEventos(Double valorEventos) {
        this.valorEventos = valorEventos;
    }

    public boolean isDiscapacitados() {
        return discapacitados;
    }

    public void setDiscapacitados(boolean discapacitados) {
        this.discapacitados = discapacitados;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isSerHabitacion() {
        return serHabitacion;
    }

    public void setSerHabitacion(boolean serHabitacion) {
        this.serHabitacion = serHabitacion;
    }

    public boolean isSerParqueadero() {
        return serParqueadero;
    }

    public void setSerParqueadero(boolean serParqueadero) {
        this.serParqueadero = serParqueadero;
    }

    public boolean isCafeBar() {
        return cafeBar;
    }

    public void setCafeBar(boolean cafeBar) {
        this.cafeBar = cafeBar;
    }

    public boolean isSpa() {
        return spa;
    }

    public void setSpa(boolean spa) {
        this.spa = spa;
    }

    public boolean isGimnasio() {
        return gimnasio;
    }

    public void setGimnasio(boolean gimnacio) {
        this.gimnasio = gimnacio;
    }

    public boolean isPiscina() {
        return piscina;
    }

    public void setPiscina(boolean piscina) {
        this.piscina = piscina;
    }

    public boolean isJacuzzi() {
        return jacuzzi;
    }

    public void setJacuzzi(boolean jacuzzi) {
        this.jacuzzi = jacuzzi;
    }

    public boolean isBanoTurco() {
        return banoTurco;
    }

    public void setBanoTurco(boolean banoTurco) {
        this.banoTurco = banoTurco;
    }

    public boolean isTina() {
        return tina;
    }

    public void setTina(boolean tina) {
        this.tina = tina;
    }

    public boolean isSauna() {
        return sauna;
    }

    public void setSauna(boolean sauna) {
        this.sauna = sauna;
    }

    public boolean isSalaNinos() {
        return salaNinos;
    }

    public void setSalaNinos(boolean salaNinos) {
        this.salaNinos = salaNinos;
    }

    public boolean isRefrigerador() {
        return refrigerador;
    }

    public void setRefrigerador(boolean refrigerador) {
        this.refrigerador = refrigerador;
    }

    public boolean isMicrohondas() {
        return microhondas;
    }

    public void setMicrohondas(boolean microhondas) {
        this.microhondas = microhondas;
    }

    public boolean isTelevision() {
        return television;
    }

    public void setTelevision(boolean television) {
        this.television = television;
    }

    public boolean isEquipoSonido() {
        return equipoSonido;
    }

    public void setEquipoSonido(boolean equipoSonido) {
        this.equipoSonido = equipoSonido;
    }

    public boolean isCajaFuerte() {
        return cajaFuerte;
    }

    public void setCajaFuerte(boolean cajaFuerte) {
        this.cajaFuerte = cajaFuerte;
    }

    public boolean isTeatro() {
        return teatro;
    }

    public void setTeatro(boolean teatro) {
        this.teatro = teatro;
    }

    public Localizacion getLocaliza() {
        return localiza;
    }

    public void setLocaliza(Localizacion localizacion) {
        this.localiza = localizacion;
    }
}
