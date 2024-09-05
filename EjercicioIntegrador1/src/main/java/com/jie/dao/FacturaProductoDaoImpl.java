package com.jie.dao;

import com.jie.model.FacturaProducto;
import com.jie.util.HelperMySql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class FacturaProductoDaoImpl implements Dao<FacturaProducto> {
    @Override
    public void createTable() throws SQLException {
        try (Connection conn = HelperMySql.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "CREATE TABLE factura_producto (" +
                    "id_factura INT," +
                    "id_producto INT," +
                    "cantidad INT," +
                    "PRIMARY KEY (id_factura, id_producto)," +
                    "FOREIGN KEY (id_factura) REFERENCES factura(id_factura)," +
                    "FOREIGN KEY (id_producto) REFERENCES producto(id_producto)";
            stmt.execute(query);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void add(FacturaProducto facturaProducto) {

    }

    @Override
    public void update(FacturaProducto facturaProducto, Map<String, Object> updates) {

    }

    @Override
    public void delete(FacturaProducto facturaProducto) {

    }

    @Override
    public FacturaProducto get(int id) {
        return null;
    }

    @Override
    public List<FacturaProducto> getAll() {
        return List.of();
    }
}
