package com.example.turnofast.modelos;

public class Dia {
    private int nro;
    private String nombre;

    public Dia(int nro, String nombre) {
        this.nro = nro;
        this.nombre = nombre;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Dia{" +
                "nro=" + nro +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
