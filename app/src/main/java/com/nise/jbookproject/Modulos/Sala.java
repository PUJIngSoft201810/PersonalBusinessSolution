package com.nise.jbookproject.Modulos;

import java.io.Serializable;

public class Sala extends Recurso implements Serializable{
    int capacidad;

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Sala(String id, String nombre, String descripcion, boolean estado, String ubicacion, int capacidad) {
        super(id, nombre,descripcion, estado, ubicacion);
        this.capacidad = capacidad;
    }

    public Sala(String id, String nombre, String descripcion, boolean estado, String ubicacion) {
        super(id, nombre, descripcion, estado, ubicacion);
    }

    public Sala(){
        //FireBase
        super();
    }

    @Override
    public String toString() {
        return "Sala{" +
                "capacidad=" + capacidad +
                ", id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", reservado=" + reservado +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}