package com.jie.dao;

import com.jie.factory.Factory;
import com.jie.model.FacturaProducto;
import com.jie.util.HelperMySql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class FacturaProductoDaoImpl implements Dao<FacturaProducto> {
    private Factory factory;

    public FacturaProductoDaoImpl(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void createTable() throws SQLException {
        try (Connection conn = factory.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "CREATE TABLE factura_producto (" +
                    "id_factura INT," +
                    "id_producto INT," +
                    "cantidad INT," +
                    "FOREIGN KEY (id_factura) REFERENCES factura(id_factura)," +
                    "FOREIGN KEY (id_producto) REFERENCES producto(id_producto))";
            stmt.execute(query);
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void add(FacturaProducto facturaProducto) throws SQLException {
        String query = "INSERT INTO factura_producto VALUES (?,?,?)";

        try (Connection conn = factory.getConnection();
        PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, facturaProducto.getFactura().getId());
            pstmt.setInt(2, facturaProducto.getProducto().getId());
            pstmt.setInt(3, facturaProducto.getCantidad());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(FacturaProducto facturaProducto) throws SQLException {

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
