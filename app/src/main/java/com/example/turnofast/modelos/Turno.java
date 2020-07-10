package com.example.turnofast.modelos;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private int id;
    private String fecha;
    private LocalTime hora;
    private int horarioDisponibleId;
    private int usuarioId;
    private Prestacion prestacion;
    private Usuario usuario;

    public Turno() {
    }

    public Turno(int id, String fecha, LocalTime hora, int horarioDisponibleId, int usuarioId, Prestacion prestacion, Usuario usuario) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.horarioDisponibleId = horarioDisponibleId;
        this.usuarioId = usuarioId;
        this.prestacion = prestacion;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public int getHorarioDisponibleId() {
        return horarioDisponibleId;
    }

    public void setHorarioDisponibleId(int horarioDisponibleId) {
        this.horarioDisponibleId = horarioDisponibleId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Prestacion getPrestacion() {
        return prestacion;
    }

    public void setPrestacion(Prestacion prestacion) {
        this.prestacion = prestacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
