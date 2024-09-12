package com.jie.service;

import com.jie.dao.Dao;
import com.jie.factory.Factory;
import com.jie.model.Factura;

import java.sql.SQLException;

public class ServicioFacturas {
    private Dao<Factura> daoFactura;

    public ServicioFacturas(Factory factory) {
        this.daoFactura = factory.getFacturaDao();
    }

    public void createTable() {
        try {
            this.daoFactura.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
