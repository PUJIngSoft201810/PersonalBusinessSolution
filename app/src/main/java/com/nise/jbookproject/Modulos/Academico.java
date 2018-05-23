package com.nise.jbookproject.Modulos;

public class Academico extends Usuario {
    Eacademico Estado;

    public Academico(String id, String email, String nombre, String apellido, String identificacion, Eacademico estado) {
        super(id, email, nombre, apellido, identificacion);
        Estado = estado;
    }

    public Eacademico getEstado() {
        return Estado;
    }

    public void setEstado(Eacademico estado) {
        Estado = estado;
    }

    @Override
    public String toString() {
        return "Academico{" +
                "Estado=" + Estado +
                ", id='" + id + '\'' +
                '}';
    }
}