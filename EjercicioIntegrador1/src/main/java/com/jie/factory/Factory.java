package com.jie.factory;

import com.jie.dao.Dao;
import com.jie.model.Cliente;
import com.jie.model.Factura;
import com.jie.model.FacturaProducto;
import com.jie.model.Producto;

import java.sql.Connection;
import java.sql.SQLException;

public interface Factory {
    Connection getConnection() throws SQLException;
    Dao<Cliente> getClienteDao();
    Dao<Factura> getFacturaDao();
    Dao<Producto> getProductoDao();
    Dao<FacturaProducto> getFacturaProductoDao();
}
