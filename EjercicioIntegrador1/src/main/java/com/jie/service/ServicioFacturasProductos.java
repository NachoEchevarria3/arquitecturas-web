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


    public FacturasProductos findById(int idFactura, int idProducto) {
        try {
            return this.daoFacturaProducto.get(int idFactura, int idProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FacturasProductos> findAll() {
        try {
            return this.daoFacturaProducto.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(FacturasProductos facturaProducto) {
        if (facturaProducto == null) throw new IllegalArgumentException("La factura no puede ser nulo");
        try {
            this.daoFacturaProducto.delete(facturaProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(FacturasProductos facturaProducto) {
        if (facturaProducto == null) throw new IllegalArgumentException("La factura no puede ser nula");
        try {
            this.daoFacturaProducto.update(facturaProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
