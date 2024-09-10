package com.jie.service;

import com.jie.dao.Dao;
import com.jie.dao.FacturaProductoDaoImpl;
import com.jie.model.FacturaProducto;

import java.sql.SQLException;

public class ServicioFacturasProductos {
    private Dao<FacturaProducto> daoFacturaProducto;

    public ServicioFacturasProductos() {
        this.daoFacturaProducto = new FacturaProductoDaoImpl();
    }

    public void add(FacturaProducto facturaProducto) {
        if (facturaProducto == null) throw new IllegalArgumentException("FacturaProducto no puede ser nulo");
        try {
            this.daoFacturaProducto.add(facturaProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
