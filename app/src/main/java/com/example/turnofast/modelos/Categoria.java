package com.example.turnofast.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Categoria implements Serializable {
    private int id;
    private String nombre;
    private ArrayList<Prestacion> prestaciones;

    public Categoria() {
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

    public ArrayList<Prestacion> getPrestaciones() {
        return prestaciones;
    }

    public void setPrestaciones(ArrayList<Prestacion> prestaciones) {
        this.prestaciones = prestaciones;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
