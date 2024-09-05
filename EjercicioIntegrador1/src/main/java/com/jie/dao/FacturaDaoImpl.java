package com.jie.dao;

import com.jie.model.Factura;
import com.jie.util.HelperMySql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class FacturaDaoImpl implements Dao<Factura> {
    @Override
    public void createTable() throws SQLException {
        try (Connection conn = HelperMySql.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "CREATE TABLE factura (" +
                    "id_factura INT," +
                    "id_cliente INT," +
                    "PRIMARY KEY (id_factura)," +
                    "FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente))";
            stmt.execute(query);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void add(Factura factura) {

    }

    @Override
    public void update(Factura factura, Map<String, Object> updates) {

    }

    @Override
    public void delete(Factura factura) {

    }

    @Override
    public Factura get(int id) {
        return null;
    }

    @Override
    public List<Factura> getAll() {
        return List.of();
    }
}
