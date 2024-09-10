package com.jie.dao;

import com.jie.factory.Factory;
import com.jie.model.Producto;
import com.jie.util.HelperMySql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDaoImpl implements Dao<Producto> {
    private Factory factory;

    public ProductoDaoImpl(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void createTable() throws SQLException {
        try (Connection conn = factory.getConnection();
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
    public void add(Producto producto) throws SQLException {
        String query = "INSERT INTO producto (id_producto, nombre, valor) VALUES (?, ?, ?)";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, producto.getId());
            pstmt.setString(2, producto.getNombre());
            pstmt.setFloat(3, producto.getPrecio());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Producto producto) throws SQLException {
        String query = "UPDATE producto SET id_producto = ?, nombre = ? WHERE valor = ?";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, producto.getId());
            pstmt.setString(2, producto.getNombre());
            pstmt.setFloat(3, producto.getPrecio());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(Producto producto) throws SQLException {
        String query = "DELETE FROM producto WHERE id_producto = ?";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)){
            pstmt.setInt(1, producto.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public Producto get(int id) throws SQLException {
        Producto producto = null;
        String query = "SELECT * FROM producto WHERE id_producto = ?";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                producto = new Producto(rs.getInt("id_producto"), rs.getString("nombre"), rs.getFloat("valor"));
            }
        }

        return producto;
    }

    @Override
    public List<Producto> getAll() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM producto";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
             ResultSet rs = pstmt.executeQuery();
             while (rs.next()) {
                 productos.add(new Producto(rs.getInt("id_producto"), rs.getString("nombre"), rs.getFloat("valor")));
             }
        }
    }
}
