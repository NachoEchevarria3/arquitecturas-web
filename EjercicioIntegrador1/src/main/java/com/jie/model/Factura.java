package com.jie.model;

public class Factura {
    private int id;
    private Cliente cliente;

    public Factura(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", cliente=" + cliente +
                '}';
    }
}
