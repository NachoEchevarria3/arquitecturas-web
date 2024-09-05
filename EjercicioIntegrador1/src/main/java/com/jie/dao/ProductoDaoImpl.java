package com.jie.dao;

import com.jie.model.Producto;
import com.jie.util.HelperMySql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class ProductoDaoImpl implements Dao<Producto> {
    @Override
    public void createTable() throws SQLException {
        try (Connection conn = HelperMySql.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "CREATE TABLE producto (" +
                    "id_producto INT," +
                    "nombre VARCHAR(45)," +
                    "valor FLOAT," +
                    "PRIMARY KEY (id_producto))";
            stmt.execute(query);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void add(Producto producto) {

    }

    @Override
    public void update(Producto producto, Map<String, Object> updates) {

    }

    @Override
    public void delete(Producto producto) {

    }

    @Override
    public Producto get(int id) {
        return null;
    }

    @Override
    public List<Producto> getAll() {
        return List.of();
    }
}
