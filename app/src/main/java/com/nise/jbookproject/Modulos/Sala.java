package com.nise.jbookproject.Modulos;

public class Sala extends Recurso {
    int capacidad;

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Sala(String id, String descripcion, boolean estado, String ubicacion, int capacidad) {
        super(id, descripcion, estado, ubicacion);
        this.capacidad = capacidad;
    }

    public Sala(String id, String descripcion, boolean estado, String ubicacion) {
        super(id, descripcion, estado, ubicacion);
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