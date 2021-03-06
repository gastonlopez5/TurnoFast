package com.tuempresa.turnofast.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Rubro implements Serializable {
    private int id;
    private String tipo;
    private String rutaFoto;
    private ArrayList<Categoria> especialidades;

    public Rubro() {
    }

    public Rubro(int id, String tipo, String rutaFoto, ArrayList<Categoria> especialidades) {
        this.id = id;
        this.tipo = tipo;
        this.rutaFoto = rutaFoto;
        this.especialidades = especialidades;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public ArrayList<Categoria> getEspecialidades() {
        return especialidades;
    }

    public void setEspecialidades(ArrayList<Categoria> especialidades) {
        this.especialidades = especialidades;
    }
}
