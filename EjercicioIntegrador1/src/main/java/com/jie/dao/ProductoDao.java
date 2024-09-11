package com.jie.dao;

import com.jie.dto.ProductoDto;
import com.jie.model.Producto;

import java.sql.SQLException;

public interface ProductoDao extends Dao<Producto> {
    ProductoDto obtenerProductoQueMasRecaudo() throws SQLException;
}
