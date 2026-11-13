/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalTime;

/**
 *
 * @author estef
 */
public class ClaseGym {
    private int idClase;
    private String disciplina;
    private Entrenador entrenador;
    private LocalTime horario;
    private int capacidad;
    private boolean estado;

    public ClaseGym() {
    }

    public ClaseGym(String disciplina, Entrenador entrenador, LocalTime horario, int capacidad, boolean estado) {
        this.disciplina = disciplina;
        this.entrenador = entrenador;
        this.horario = horario;
        this.capacidad = capacidad;
        this.estado = estado;
    }
       
    public ClaseGym(int idClase, String disciplina, Entrenador entrenador, LocalTime horario, int capacidad, boolean estado) {
        this.idClase = idClase;
        this.disciplina = disciplina;
        this.entrenador = entrenador;
        this.horario = horario;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getIdClase() {
        return idClase;
    }

    public void setIdClase(int idClase) {
        this.idClase = idClase;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(Entrenador entrenador) {
        this.entrenador = entrenador;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ID: " + idClase + " - " + disciplina + " - " + entrenador.getApellido()+", "+ entrenador.getNombre() + ", horario=" + horario;
    }    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.idClase;
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
        final ClaseGym other = (ClaseGym) obj;
        return this.idClase == other.idClase;
    }
}

