package com.example.carwashdriver_android.Models;

public class DetailsModel {
    int id;
    String servicio;
    String notaAdministrador;
    int estado;
    String nota;
    private int contadorMultimedia;
    String nombreCliente, marca,color,placa;
    double lon,lat;

    //para gets de la informacion principal
    public DetailsModel(double lat, double lon, String placa, String color, String marca, String nombreCliente) {
        this.lat = lat;
        this.lon = lon;
        this.placa = placa;
        this.color = color;
        this.marca = marca;
        this.nombreCliente = nombreCliente;
    }

    public DetailsModel(String placa, String color, String marca, String nombreCliente) {
        this.placa = placa;
        this.color = color;
        this.marca = marca;
        this.nombreCliente = nombreCliente;
    }


    //para gets de los servicios
    public DetailsModel(int id, String servicio, String notaAdministrador, int estado) {
        this.id = id;
        this.servicio = servicio;
        this.notaAdministrador = notaAdministrador;
        this.estado = estado;
    }

    //para posts
    public DetailsModel(int id, int estado, String nota) {
        this.id = id;
        this.estado = estado;
        this.nota = nota;
    }

    public int getContadorMultimedia() {
        return contadorMultimedia;
    }

    public void setContadorMultimedia(int contadorMultimedia) {
        this.contadorMultimedia = contadorMultimedia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getNotaAdministrador() {
        return notaAdministrador;
    }

    public void setNotaAdministrador(String notaAdministrador) {
        this.notaAdministrador = notaAdministrador;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "DetailsModel{" +
                "id=" + id +
                ", servicio='" + servicio + '\'' +
                ", notaAdministrador='" + notaAdministrador + '\'' +
                ", estado=" + estado +
                ", nota='" + nota + '\'' +
                ", nombreCLiente='" + nombreCliente + '\'' +
                ", marca='" + marca + '\'' +
                ", color='" + color + '\'' +
                ", placa='" + placa + '\'' +
                ", lon=" + lon +
                ", lat=" + lat +
                '}';
    }
}

