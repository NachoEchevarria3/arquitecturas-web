package com.jie.dao;

import com.jie.model.FacturaProducto;

import java.sql.SQLException;

public interface FacturaProductoDao extends Dao<FacturaProducto> {
    FacturaProducto get(int idFactura, int idProducto) throws SQLException;
}
