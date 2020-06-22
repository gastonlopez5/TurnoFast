package com.example.turnofast.modelos;

import java.time.LocalDate;
import java.time.LocalTime;

public class Turno {
    private int id;
    private LocalDate fecha;
    private LocalTime hora;
    private String observaciones;
    private String archivoAdjunto;
    private int horarioDisponibleId;
    private int usuarioId;
    private HorarioDisponible horarioDisponible;
    private Usuario usuario;

    public Turno() {
    }

    public Turno(int id, LocalDate fecha, LocalTime hora, String observaciones, String archivoAdjunto, int horarioDisponibleId, int usuarioId, HorarioDisponible horarioDisponible, Usuario usuario) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.observaciones = observaciones;
        this.archivoAdjunto = archivoAdjunto;
        this.horarioDisponibleId = horarioDisponibleId;
        this.usuarioId = usuarioId;
        this.horarioDisponible = horarioDisponible;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(String archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
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

    public HorarioDisponible getHorarioDisponible() {
        return horarioDisponible;
    }

    public void setHorarioDisponible(HorarioDisponible horarioDisponible) {
        this.horarioDisponible = horarioDisponible;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
