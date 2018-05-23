package com.nise.jbookproject.Modulos;

public class Administrador extends Usuario {
    public Administrador(String id, String email, String nombre, String apellido, String identificacion) {
        super(id, email, nombre, apellido, identificacion);
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "idref='" + idref + '\'' +
                '}';
    }
}