package com.jie.factory;

import com.jie.dao.*;
import com.jie.model.Cliente;
import com.jie.model.Factura;
import com.jie.model.FacturaProducto;
import com.jie.model.Producto;
import com.jie.util.HelperDerby;

import java.sql.Connection;
import java.sql.SQLException;

public class DerbyFactory implements Factory {
    @Override
    public Connection getConnection() throws SQLException {
        return HelperDerby.getConnection();
    }

    @Override
    public Dao<Cliente> getClienteDao() {
        return new ClienteDaoImpl(this);
    }

    @Override
    public Dao<Factura> getFacturaDao() {
        return new FacturaDaoImpl(this);
    }

    @Override
    public Dao<Producto> getProductoDao() {
        return new ProductoDaoImpl(this);
    }

    @Override
    public Dao<FacturaProducto> getFacturaProductoDao() {
        return new FacturaProductoDaoImpl(this);
    }
}
