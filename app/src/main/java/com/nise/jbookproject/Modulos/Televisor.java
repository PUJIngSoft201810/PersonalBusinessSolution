package com.nise.jbookproject.Modulos;

public class Televisor extends Recurso {

    public Televisor(String id, String descripcion, boolean estado, String ubicacion) {
        super(id, descripcion, estado, ubicacion);
    }

    @Override
    public String toString() {
        return "Televisor{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", reservado=" + reservado +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}