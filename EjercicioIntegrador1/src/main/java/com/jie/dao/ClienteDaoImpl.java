package com.jie.dao;

import com.jie.factory.Factory;
import com.jie.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDaoImpl implements Dao<Cliente> {
    private Factory factory;

    public ClienteDaoImpl(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void createTable() throws SQLException {
        try (Connection conn = factory.getConnection();
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
    public void add(Cliente cliente) throws SQLException {
        String query = "INSERT INTO cliente (id_cliente, nombre, email) VALUES (?, ?, ?)";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cliente.getId());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getEmail());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        String query = "UPDATE cliente SET nombre = ?, email = ? WHERE id_cliente = ?";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getEmail());
            pstmt.setInt(3, cliente.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Cliente cliente) throws SQLException {
        String query = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, cliente.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Cliente get(int id) throws SQLException {
        Cliente cliente = null;
        String query = "SELECT * FROM cliente WHERE id_cliente = ?";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cliente = new Cliente(rs.getInt("id_cliente"), rs.getString("nombre"), rs.getString("email"));
            }
        }

        return cliente;
    }

    @Override
    public List<Cliente> getAll() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM cliente";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id_cliente"), rs.getString("nombre"), rs.getString("email")));
            }
        }

        return clientes;
    }
}
