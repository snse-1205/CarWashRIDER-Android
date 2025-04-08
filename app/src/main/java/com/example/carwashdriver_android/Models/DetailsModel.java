package com.example.carwashdriver_android.Models;

import org.jetbrains.annotations.NonNls;

import java.util.List;

public class DetailsModel {
    int id;
    String servicio;
    String notaAdministrador;
    int estado;
    List<String> multimedia;

    //para gets
    public DetailsModel(int id, String servicio, String notaAdministrador, int estado) {
        this.id = id;
        this.servicio = servicio;
        this.notaAdministrador = notaAdministrador;
        this.estado = estado;
    }

    //para posts
    public DetailsModel(int id, int estado, List<String> multimedia) {
        this.id = id;
        this.estado = estado;
        this.multimedia = multimedia;
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

    public List<String> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<String> multimedia) {
        this.multimedia = multimedia;
    }
}

