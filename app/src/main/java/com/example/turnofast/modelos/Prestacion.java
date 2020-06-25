package com.example.turnofast.modelos;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class Prestacion {
    private int id;
    private String diaInicioManiana;
    private String getDiaInicioTarde;
    private LocalDate fechaInicioManiana;
    private LocalDate fechaFinManiana;
    private LocalDate fechaInicioTarde;
    private LocalDate fechaFinTarde;
    private LocalTime horaInicioManiana;
    private LocalTime horaFinManiana;
    private LocalTime horaInicioTarde;
    private LocalTime horaFinTarde;
    private int frecuencia;
    private String telefono;
    private String direccion;
    private String nombre;
    private String logo;
    private Boolean disponible;
    private int categoriaId;
    private int servicioId;
    private Usuario profesional;
    private Categoria categoria;
    private ArrayList<Turno> turnos;

    public Prestacion() {
    }

    public Prestacion(int id, String diaInicioManiana, String getDiaInicioTarde, LocalDate fechaInicioManiana, LocalDate fechaFinManiana, LocalDate fechaInicioTarde, LocalDate fechaFinTarde, LocalTime horaInicioManiana, LocalTime horaFinManiana, LocalTime horaInicioTarde, LocalTime horaFinTarde, int frecuencia, String telefono, String direccion, String nombre, String logo, Boolean disponible, int categoriaId, int servicioId, Usuario profesional, Categoria categoria, ArrayList<Turno> turnos) {
        this.id = id;
        this.diaInicioManiana = diaInicioManiana;
        this.getDiaInicioTarde = getDiaInicioTarde;
        this.fechaInicioManiana = fechaInicioManiana;
        this.fechaFinManiana = fechaFinManiana;
        this.fechaInicioTarde = fechaInicioTarde;
        this.fechaFinTarde = fechaFinTarde;
        this.horaInicioManiana = horaInicioManiana;
        this.horaFinManiana = horaFinManiana;
        this.horaInicioTarde = horaInicioTarde;
        this.horaFinTarde = horaFinTarde;
        this.frecuencia = frecuencia;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombre = nombre;
        this.logo = logo;
        this.disponible = disponible;
        this.categoriaId = categoriaId;
        this.servicioId = servicioId;
        this.profesional = profesional;
        this.categoria = categoria;
        this.turnos = turnos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDiaInicioManiana() {
        return diaInicioManiana;
    }

    public void setDiaInicioManiana(String diaInicioManiana) {
        this.diaInicioManiana = diaInicioManiana;
    }

    public String getGetDiaInicioTarde() {
        return getDiaInicioTarde;
    }

    public void setGetDiaInicioTarde(String getDiaInicioTarde) {
        this.getDiaInicioTarde = getDiaInicioTarde;
    }

    public LocalDate getFechaInicioManiana() {
        return fechaInicioManiana;
    }

    public void setFechaInicioManiana(LocalDate fechaInicioManiana) {
        this.fechaInicioManiana = fechaInicioManiana;
    }

    public LocalDate getFechaFinManiana() {
        return fechaFinManiana;
    }

    public void setFechaFinManiana(LocalDate fechaFinManiana) {
        this.fechaFinManiana = fechaFinManiana;
    }

    public LocalDate getFechaInicioTarde() {
        return fechaInicioTarde;
    }

    public void setFechaInicioTarde(LocalDate fechaInicioTarde) {
        this.fechaInicioTarde = fechaInicioTarde;
    }

    public LocalDate getFechaFinTarde() {
        return fechaFinTarde;
    }

    public void setFechaFinTarde(LocalDate fechaFinTarde) {
        this.fechaFinTarde = fechaFinTarde;
    }

    public LocalTime getHoraInicioManiana() {
        return horaInicioManiana;
    }

    public void setHoraInicioManiana(LocalTime horaInicioManiana) {
        this.horaInicioManiana = horaInicioManiana;
    }

    public LocalTime getHoraFinManiana() {
        return horaFinManiana;
    }

    public void setHoraFinManiana(LocalTime horaFinManiana) {
        this.horaFinManiana = horaFinManiana;
    }

    public LocalTime getHoraInicioTarde() {
        return horaInicioTarde;
    }

    public void setHoraInicioTarde(LocalTime horaInicioTarde) {
        this.horaInicioTarde = horaInicioTarde;
    }

    public LocalTime getHoraFinTarde() {
        return horaFinTarde;
    }

    public void setHoraFinTarde(LocalTime horaFinTarde) {
        this.horaFinTarde = horaFinTarde;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
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

    public int getServicioId() {
        return servicioId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
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

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<Turno> turnos) {
        this.turnos = turnos;
    }
}
