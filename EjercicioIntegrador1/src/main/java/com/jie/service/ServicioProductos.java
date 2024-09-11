package com.jie.service;

import com.jie.dao.Dao;
import com.jie.factory.Factory;
import com.jie.model.Producto;

import java.sql.SQLException;
import java.util.List;

public class ServicioProductos {
    private Dao<Producto> daoProductos;

    public ServicioProductos(Factory factory) {
        this.daoProductos = factory.getProductoDao();
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

    public List<Producto> findAll(){
        try {
            return this.daoProductos.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser nulo");
        try {
            this.daoProductos.delete(producto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public void update(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("El producto no puede ser nulo");
        try {
            this.daoProductos.update(producto);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
