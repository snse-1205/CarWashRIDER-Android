package com.example.carwashdriver_android.Models;

public class ToDoModel {
    int id;
    int modalidad;
    int estado;
    String fechaCita;
    String horaTrabajo;

    public ToDoModel() {}

    public ToDoModel(int id, int modalidad, int estado, String fechaCita, String horaTrabajo) {
        this.id = id;
        this.modalidad = modalidad;
        this.estado = estado;
        this.fechaCita = fechaCita;
        this.horaTrabajo = horaTrabajo;
    }

    public String getHoraTrabajo() {
        return horaTrabajo;
    }

    public void setHoraTrabajo(String horaTrabajo) {
        this.horaTrabajo = horaTrabajo;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getModalidad() {
        return modalidad;
    }

    public void setModalidad(int modalidad) {
        this.modalidad = modalidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
