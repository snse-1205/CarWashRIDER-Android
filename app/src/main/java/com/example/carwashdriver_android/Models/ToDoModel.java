package com.example.carwashdriver_android.Models;

public class ToDoModel {
    int id;
    int modalidad;
    int estado;
    String placa;
    String color;
    String marca;
    String fechaCita;
    String horaTrabajo;


    public ToDoModel() {}

    public ToDoModel(int id, int modalidad, int estado, String placa, String color, String marca, String fechaCita, String horaTrabajo) {
        this.id = id;
        this.placa = placa;
        this.color = color;
        this.marca = marca;
        this.modalidad = modalidad;
        this.fechaCita = fechaCita;
        this.horaTrabajo = horaTrabajo;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        color = color;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        marca = marca;
    }

    public int getModalidad() {
        return modalidad;
    }

    public void setModalidad(int modalidad) {
        this.modalidad = modalidad;
    }

    public String getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(String fechaCita) {
        this.fechaCita = fechaCita;
    }

    public String getHoraTrabajo() {
        return horaTrabajo;
    }

    public void setHoraTrabajo(String horaTrabajo) {
        this.horaTrabajo = horaTrabajo;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
}
