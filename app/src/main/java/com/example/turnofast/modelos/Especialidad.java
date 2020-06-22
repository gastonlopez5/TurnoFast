package com.example.turnofast.modelos;

import java.util.ArrayList;

public class Especialidad {
    private int id;
    private String nombre;
    private ArrayList<Servicio> servicios;

    public Especialidad() {
    }

    public Especialidad(int id, String nombre, ArrayList<Servicio> servicios) {
        this.id = id;
        this.nombre = nombre;
        this.servicios = servicios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(ArrayList<Servicio> servicios) {
        this.servicios = servicios;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
