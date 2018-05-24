package com.nise.jbookproject.Modulos;

public class Consola extends Recurso {
    int controles;

    public int getControles() {
        return controles;
    }

    public void setControles(int controles) {
        this.controles = controles;
    }

    public Consola(String id, String descripcion, boolean estado, String ubicacion, int controles) {
        super(id, descripcion, estado, ubicacion);
        this.controles = controles;
    }

    public Consola(String id, String descripcion, boolean estado, String ubicacion) {
        super(id, descripcion, estado, ubicacion);
    }

    public  Consola(){

    }

    @Override
    public String toString() {
        return "Consola{" +
                "controles=" + controles +
                ", id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", reservado=" + reservado +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}

