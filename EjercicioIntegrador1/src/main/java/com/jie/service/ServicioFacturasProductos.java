package com.jie.service;

import com.jie.dao.FacturaProductoDao;
import com.jie.factory.Factory;
import com.jie.model.FacturaProducto;

import java.sql.SQLException;
import java.util.List;

public class ServicioFacturasProductos {
    private FacturaProductoDao daoFacturaProducto;

    public ServicioFacturasProductos(Factory factory) {
        this.daoFacturaProducto = (FacturaProductoDao) factory.getFacturaProductoDao();
    }

    public void createTable() {
        try {
            this.daoFacturaProducto.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(FacturaProducto facturaProducto) {
        if (facturaProducto == null) throw new IllegalArgumentException("FacturaProducto no puede ser nulo");
        try {
            this.daoFacturaProducto.add(facturaProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public FacturaProducto findById(int idFactura, int idProducto) {
        try {
            return this.daoFacturaProducto.get(idFactura, idProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FacturaProducto> findAll() {
        try {
            return this.daoFacturaProducto.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(FacturaProducto facturaProducto) {
        if (facturaProducto == null) throw new IllegalArgumentException("La factura no puede ser nulo");
        try {
            this.daoFacturaProducto.delete(facturaProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(FacturaProducto facturaProducto) {
        if (facturaProducto == null) throw new IllegalArgumentException("La factura no puede ser nula");
        try {
            this.daoFacturaProducto.update(facturaProducto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
