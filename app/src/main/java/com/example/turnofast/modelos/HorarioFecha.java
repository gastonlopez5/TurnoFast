package com.example.turnofast.modelos;

import java.io.Serializable;
import java.time.LocalDate;

public class HorarioFecha implements Serializable {
    private Horario2 horario;
    private String fecha;

    public HorarioFecha() {
    }

    public HorarioFecha(Horario2 horario, String fecha) {
        this.horario = horario;
        this.fecha = fecha;
    }

    public Horario2 getHorario() {
        return horario;
    }

    public void setHorario(Horario2 horario) {
        this.horario = horario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
