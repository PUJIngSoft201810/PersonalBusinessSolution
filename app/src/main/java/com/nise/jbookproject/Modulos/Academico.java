package com.nise.jbookproject.Modulos;

public class Academico extends Usuario {
    private   Eacademico Estado;

    public Academico(String id, String email, String nombre, String apellido, String identificacion, Eacademico estadoAcad) {
        super(id, email, nombre, apellido, identificacion);
        this.Estado = estadoAcad;
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
                '}';
    }
}