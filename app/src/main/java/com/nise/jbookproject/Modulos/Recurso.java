package com.nise.jbookproject.Modulos;

import java.io.Serializable;

public class Recurso implements Serializable{
    String id;
    String descripcion;
    //estado = 0 disponible
    //       = 1 reservado

    boolean reservado;
    String ubicacion;
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isReservado() {
        return reservado;
    }

    public void setReservado(boolean reservado) {
        this.reservado = reservado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Recurso(){

    }
    public Recurso(String id, String descripcion, boolean reservado, String ubicacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.reservado = reservado;
        this.ubicacion = ubicacion;
    }

    public Recurso(String id, String nombre, String descripcion, boolean reservado, String ubicacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.reservado = reservado;
        this.ubicacion = ubicacion;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Recurso{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", reservado=" + reservado +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
