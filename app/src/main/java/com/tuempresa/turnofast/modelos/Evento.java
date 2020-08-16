package com.tuempresa.turnofast.modelos;

public class Evento {
    private String evento, hota, fecha, mes, anio;

    public Evento() {
    }

    public Evento(String evento, String hota, String fecha, String mes, String anio) {
        this.evento = evento;
        this.hota = hota;
        this.fecha = fecha;
        this.mes = mes;
        this.anio = anio;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getHota() {
        return hota;
    }

    public void setHota(String hota) {
        this.hota = hota;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
}
