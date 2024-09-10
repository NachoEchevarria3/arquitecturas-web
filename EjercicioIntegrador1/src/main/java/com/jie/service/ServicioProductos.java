package com.jie.service;

import com.jie.dao.Dao;
import com.jie.dao.ProductoDaoImpl;
import com.jie.model.Producto;

import java.sql.SQLException;

public class ServicioProductos {
    private Dao<Producto> daoProductos;

    public ServicioProductos() {
        this.daoProductos = new ProductoDaoImpl();
    }

    public void add(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser nulo");
        try {
            this.daoProductos.add(producto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Producto findById(int id) {
        try {
            return this.daoProductos.get(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
