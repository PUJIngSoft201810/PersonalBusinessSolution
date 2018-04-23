package com.nise.jbookproject.Modulos;

public class Reserva {
    String idReserva;
    int idUsuario;
    String idRecurso;
    String recurso;

    public Reserva() {
    }

    public Reserva(String idReserva, int idUsuario, String idRecurso, String recurso) {
        this.idReserva = idReserva;
        this.idUsuario = idUsuario;
        this.idRecurso = idRecurso;
        this.recurso = recurso;
    }

    public String getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(String idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(String idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getRecurso() {
        return recurso;
    }

    public void setRecurso(String recurso) {
        this.recurso = recurso;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "idReserva=" + idReserva +
                ", idUsuario=" + idUsuario +
                ", idRecurso=" + idRecurso +
                ", recurso='" + recurso + '\'' +
                '}';
    }
}

