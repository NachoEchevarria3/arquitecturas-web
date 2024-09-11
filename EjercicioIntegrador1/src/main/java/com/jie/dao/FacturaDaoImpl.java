package com.jie.dao;

import com.jie.factory.Factory;
import com.jie.model.Cliente;
import com.jie.model.Factura;
import com.jie.service.ServicioClientes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDaoImpl implements Dao<Factura> {
    private Factory factory;

    public FacturaDaoImpl(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void createTable() throws SQLException {
        try (Connection conn = factory.getConnection();
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
    public void add(Factura factura) throws SQLException {
        String query = "INSERT INTO factura (id_factura, id_cliente) VALUES (?,?)";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, factura.getId());
            pstmt.setInt(2, factura.getCliente().getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Factura factura) throws SQLException {
        String query = "UPDATE factura SET id_cliente WHERE id_factura = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, factura.getCliente().getId());
            pstmt.setInt(2, factura.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Factura factura) throws SQLException {
        String query = "DELETE FROM factura WHERE id_factura = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, factura.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Factura get(int id) throws SQLException {
        ServicioClientes servicioClientes = new ServicioClientes(factory);
        Factura factura = null;
        String query = "SELECT * FROM factura WHERE id_factura = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = servicioClientes.findById(rs.getInt("id_cliente"));
                factura = new Factura(rs.getInt("id_factura"), cliente);
            }
        }

        return factura;
    }

    @Override
    public List<Factura> getAll() throws SQLException {
        ServicioClientes servicioClientes = new ServicioClientes(factory);
        List<Factura> facturas = new ArrayList<>();
        String query = "SELECT * FROM facturas";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = servicioClientes.findById(rs.getInt("id_cliente"));
                facturas.add(new Factura(rs.getInt("id_factura"), cliente));
            }
        }

        return facturas;
    }
}

