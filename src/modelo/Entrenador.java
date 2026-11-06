/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.Objects;

/**
 *
 * @author estef
 */
public class Entrenador {
    private int idEntrenador=-1;
    private String dni;
    private String nombre;
    private String apellido;
    private String especialidad;
    private boolean estado;

    public Entrenador() {
    }

    public Entrenador(int idEntrenador, String dni, String nombre, String apellido, String especialidad, boolean estado) {
        this.idEntrenador= idEntrenador;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public Entrenador(String dni, String nombre, String apellido, String especialidad, boolean estado) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.especialidad = especialidad;
        this.estado = estado;
    }

    public int getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(int idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ID: "+ idEntrenador +" - "+ apellido +", "+ nombre+".";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.idEntrenador;
        hash = 61 * hash + Objects.hashCode(this.dni);
        hash = 61 * hash + Objects.hashCode(this.nombre);
        hash = 61 * hash + Objects.hashCode(this.apellido);
        hash = 61 * hash + Objects.hashCode(this.especialidad);
        hash = 61 * hash + (this.estado ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Entrenador other = (Entrenador) obj;
        if (this.idEntrenador != other.idEntrenador) {
            return false;
        }
        if (this.estado != other.estado) {
            return false;
        }
        if (!Objects.equals(this.dni, other.dni)) {
            return false;
        }
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        if (!Objects.equals(this.apellido, other.apellido)) {
            return false;
        }
        return Objects.equals(this.especialidad, other.especialidad);
    }
}
