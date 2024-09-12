package com.jie.service;

import com.jie.dao.ProductoDao;
import com.jie.dto.ProductoDto;
import com.jie.factory.Factory;
import com.jie.model.Producto;

import java.sql.SQLException;
import java.util.List;

public class ServicioProductos {
    private ProductoDao daoProductos;

    public ServicioProductos(Factory factory) {
        this.daoProductos = (ProductoDao) factory.getProductoDao();
    }

    public void createTable() {
        try {
            this.daoProductos.createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public ProductoDto obtenerProductoQueMasRecaudo() {
        try {
            return this.daoProductos.obtenerProductoQueMasRecaudo();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
