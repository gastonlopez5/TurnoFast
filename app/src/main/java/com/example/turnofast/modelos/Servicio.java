package com.example.turnofast.modelos;

import java.util.ArrayList;

public class Servicio {
    private int id;
    private String email;
    private String telefono;
    private String direccion;
    private String nombre;
    private String logo;
    private Boolean disponible;
    private int especialidadId;
    private ArrayList<HorarioDisponible> horariosDisponibles;

    public Servicio() {
    }

    public Servicio(int id, String email, String telefono, String direccion, String nombre, String logo, Boolean disponible, int especialidadId, ArrayList<HorarioDisponible> horariosDisponibles) {
        this.id = id;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.nombre = nombre;
        this.logo = logo;
        this.disponible = disponible;
        this.especialidadId = especialidadId;
        this.horariosDisponibles = horariosDisponibles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getEspecialidadId() {
        return especialidadId;
    }

    public void setEspecialidadId(int especialidadId) {
        this.especialidadId = especialidadId;
    }

    public ArrayList<HorarioDisponible> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public void setHorariosDisponibles(ArrayList<HorarioDisponible> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
    }
}
