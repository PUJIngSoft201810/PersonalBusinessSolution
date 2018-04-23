package com.nise.jbookproject.Modulos;

public class Computador extends Recurso {

    TipoComputador tipo;

    public TipoComputador getTipo() {
        return tipo;
    }

    public void setTipo(TipoComputador tipo) {
        this.tipo = tipo;
    }

    public Computador(String id, String descripcion, boolean reservado, String ubicacion, TipoComputador tipo) {
        super(id, descripcion, reservado, ubicacion);
        this.tipo = tipo;
    }

    public Computador(String id, String descripcion, boolean reservado, String ubicacion) {
        super(id, descripcion, reservado, ubicacion);
    }
    public Computador(){

    }

    @Override
    public String toString() {
        return "Computador{" +
                "tipo=" + tipo +
                ", id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", reservado=" + reservado +
                ", ubicacion='" + ubicacion + '\'' +
                '}';
    }
}
