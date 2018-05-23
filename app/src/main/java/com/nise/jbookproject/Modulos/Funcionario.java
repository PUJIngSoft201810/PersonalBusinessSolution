package com.nise.jbookproject.Modulos;

public class Funcionario extends Usuario {
    public Funcionario(String id, String email, String nombre, String apellido, String identificacion) {
        super(id, email, nombre, apellido, identificacion);
    }

    @Override
    public String toString() {
        return "Funcionario{}";
    }
}