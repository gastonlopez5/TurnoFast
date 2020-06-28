package com.example.turnofast.modelos;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Prestacion implements Serializable {
    private int id;
    private String telefono;
    private String direccion;
    private String nombre;
    private String logo;
    private Boolean disponible;
    private int categoriaId;
    private int profesionalId;
    private Usuario profesional;
    private Categoria categoria;
    private ArrayList<Horario> horarios;

    public Prestacion() {
    }

    public Prestacion(int id, String telefono, String direccion, String nombre, String logo, Boolean disponible, int categoriaId, int profesionalId, Usuario profesional, Categoria categoria, ArrayList<Horario> horarios) {
        this.id = id;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombre = nombre;
        this.logo = logo;
        this.disponible = disponible;
        this.categoriaId = categoriaId;
        this.profesionalId = profesionalId;
        this.profesional = profesional;
        this.categoria = categoria;
        this.horarios = horarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getProfesionalId() {
        return profesionalId;
    }

    public void setProfesionalId(int profesionalId) {
        this.profesionalId = profesionalId;
    }

    public Usuario getProfesional() {
        return profesional;
    }

    public void setProfesional(Usuario profesional) {
        this.profesional = profesional;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(ArrayList<Horario> horarios) {
        this.horarios = horarios;
    }
}
