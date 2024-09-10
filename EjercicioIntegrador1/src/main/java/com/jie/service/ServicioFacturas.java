package com.jie.service;

import com.jie.dao.Dao;
import com.jie.dao.FacturaDaoImpl;
import com.jie.model.Factura;

import java.sql.SQLException;

public class ServicioFacturas {
    private Dao<Factura> daoFactura;

    public ServicioFacturas() {
        this.daoFactura = new FacturaDaoImpl();
    }

    public void add(Factura factura) {
        if (factura == null) throw new IllegalArgumentException("La factura no puede ser nulo");
        try {
            daoFactura.add(factura);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Factura findById(int id) {
        try {
            return this.daoFactura.get(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
