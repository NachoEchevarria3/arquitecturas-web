package com.jie.factory;

import com.jie.dao.Dao;
import com.jie.model.Cliente;
import com.jie.model.Factura;
import com.jie.model.FacturaProducto;
import com.jie.model.Producto;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class Factory {
    public enum dbTypes {
        MYSQL,
        DERBY
    }

    public abstract Connection getConnection() throws SQLException;
    public abstract Dao<Cliente> getClienteDao();
    public abstract Dao<Factura> getFacturaDao();
    public abstract Dao<Producto> getProductoDao();
    public abstract Dao<FacturaProducto> getFacturaProductoDao();

    public static Factory getFactory(dbTypes dbType) {
        switch (dbType) {
            case MYSQL:
                return MySqlFactory.getInstance();
            case DERBY:
                return DerbyFactory.getInstance();
            default:
                return null;
        }
    }
}
