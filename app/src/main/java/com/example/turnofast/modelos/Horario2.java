package com.example.turnofast.modelos;

import java.time.LocalTime;
import java.util.ArrayList;

public class Horario2 {
    private int id;
    private int diaSemana;
    private ArrayList<String> diasLaborables;
    private LocalTime horaDesdeManiana;
    private LocalTime horaHastaManiana;
    private LocalTime horaDesdeTarde;
    private LocalTime horaHastaTarde;
    private int frecuencia;
    private int prestacionId;
    private Prestacion prestacion;
    private ArrayList<Turno> turnos;

    public Horario2() {
    }

    public Horario2(int id, int diaSemana, ArrayList<String> diasLaborables, LocalTime horaDesdeManiana, LocalTime horaHastaManiana, LocalTime horaDesdeTarde, LocalTime horaHastaTarde, int frecuencia, int prestacionId, Prestacion prestacion, ArrayList<Turno> turnos) {
        this.id = id;
        this.diaSemana = diaSemana;
        this.diasLaborables = diasLaborables;
        this.horaDesdeManiana = horaDesdeManiana;
        this.horaHastaManiana = horaHastaManiana;
        this.horaDesdeTarde = horaDesdeTarde;
        this.horaHastaTarde = horaHastaTarde;
        this.frecuencia = frecuencia;
        this.prestacionId = prestacionId;
        this.prestacion = prestacion;
        this.turnos = turnos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public ArrayList<String> getDiasLaborables() {
        return diasLaborables;
    }

    public void setDiasLaborables(ArrayList<String> diasLaborables) {
        this.diasLaborables = diasLaborables;
    }

    public LocalTime getHoraDesdeManiana() {
        return horaDesdeManiana;
    }

    public void setHoraDesdeManiana(LocalTime horaDesdeManiana) {
        this.horaDesdeManiana = horaDesdeManiana;
    }

    public LocalTime getHoraHastaManiana() {
        return horaHastaManiana;
    }

    public void setHoraHastaManiana(LocalTime horaHastaManiana) {
        this.horaHastaManiana = horaHastaManiana;
    }

    public LocalTime getHoraDesdeTarde() {
        return horaDesdeTarde;
    }

    public void setHoraDesdeTarde(LocalTime horaDesdeTarde) {
        this.horaDesdeTarde = horaDesdeTarde;
    }

    public LocalTime getHoraHastaTarde() {
        return horaHastaTarde;
    }

    public void setHoraHastaTarde(LocalTime horaHastaTarde) {
        this.horaHastaTarde = horaHastaTarde;
    }

    public int getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(int frecuencia) {
        this.frecuencia = frecuencia;
    }

    public int getPrestacionId() {
        return prestacionId;
    }

    public void setPrestacionId(int prestacionId) {
        this.prestacionId = prestacionId;
    }

    public Prestacion getPrestacion() {
        return prestacion;
    }

    public void setPrestacion(Prestacion prestacion) {
        this.prestacion = prestacion;
    }

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<Turno> turnos) {
        this.turnos = turnos;
    }
}
