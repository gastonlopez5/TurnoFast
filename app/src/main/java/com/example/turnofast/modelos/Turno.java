package com.example.turnofast.modelos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Turno implements Serializable {
    private int id;
    private String fecha;
    private LocalTime hora;
    private int horarioId;
    private int usuarioId;
    private Horario2 horario2;
    private Usuario usuario;

    public Turno() {
    }

    public Turno(int id, String fecha, LocalTime hora, int horarioId, int usuarioId, Horario2 horario2, Usuario usuario) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.horarioId = horarioId;
        this.usuarioId = usuarioId;
        this.horario2 = horario2;
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

    public int getHorarioId() {
        return horarioId;
    }

    public void setHorarioId(int horarioId) {
        this.horarioId = horarioId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Horario2 getHorario2() {
        return horario2;
    }

    public void setHorario2(Horario2 horario2) {
        this.horario2 = horario2;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
