package com.jie.dao;

import com.jie.model.Cliente;
import com.jie.util.HelperMySql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

public class ClienteDaoImpl implements Dao<Cliente> {
    @Override
    public void createTable() throws SQLException {
        try (Connection conn = HelperMySql.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "CREATE TABLE cliente (" +
                    "id_cliente INT," +
                    "nombre VARCHAR(500)," +
                    "email VARCHAR(150)," +
                    "PRIMARY KEY (id_cliente))";
            stmt.execute(query);
        } catch (SQLException e) {
            throw e;
        }
    }


    @Override
    public void add(Cliente cliente) {

    }

    @Override
    public void update(Cliente cliente, Map<String, Object> updates) {

    }

    @Override
    public void delete(Cliente cliente) {

    }

    @Override
    public Cliente get(int id) {
        return null;
    }

    @Override
    public List<Cliente> getAll() {
        return List.of();
    }
}
