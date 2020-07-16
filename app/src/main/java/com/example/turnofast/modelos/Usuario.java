package com.example.turnofast.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private String telefono;
    private String email;
    private String fotoPerfil;
    private String clave;
    private Boolean estado;
    private ArrayList<Turno> turnos;
    private ArrayList<Prestacion> horariosDisponibles;

    public Usuario() {
    }

    public Usuario(int id, String nombre, String apellido, String telefono, String email, String fotoPerfil, String clave, Boolean estado, ArrayList<Turno> turnos, ArrayList<Prestacion> horariosDisponibles) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.email = email;
        this.fotoPerfil = fotoPerfil;
        this.clave = clave;
        this.estado = estado;
        this.turnos = turnos;
        this.horariosDisponibles = horariosDisponibles;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public ArrayList<Turno> getTurnos() {
        return turnos;
    }

    public void setTurnos(ArrayList<Turno> turnos) {
        this.turnos = turnos;
    }

    public ArrayList<Prestacion> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    public void setHorariosDisponibles(ArrayList<Prestacion> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
    }
}
