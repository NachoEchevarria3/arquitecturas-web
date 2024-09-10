package com.jie.dao;

import com.jie.factory.Factory;
import com.jie.model.FacturaProducto;
import com.jie.util.HelperMySql;
import com.jie.service.ServicioFacturas;
import com.jie.service.ServicioProductos;

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
        String query = "UPDATE factura_producto SET cantidad = ? WHERE id_factura = ? AND id_producto = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, facturaProducto.getCantidad());
            pstmt.setInt(2, facturaProducto.getFactura().getId());
            pstmt.setInt(3, facturaProducto.getProducto().getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(FacturaProducto facturaProducto) throws SQLException {
        String query = "DELETE FROM factura_producto WHERE id_factura = ? AND id_producto = ?";

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, facturaProducto.getFactura().getId());
            pstmt.setInt(2, facturaProducto.getProducto().getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public FacturaProducto get(int idFactura, int idProducto) throws SQLException {
        FacturaProducto facturaProducto = null;
        String query = "SELECT * FROM factura_producto WHERE id_factura = ? AND id_producto = ?";
        ServicioFacturas servicioFacturas = new ServicioFacturas();
        ServicioProductos servicioProducto = new ServicioProductos();

        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idFactura);
            pstmt.setInt(2, idProducto);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                facturaProducto = new FacturaProducto(servicioFacturas.get(rs.getInt("id_factura")), servicioProductos(rs.getInt("id_producto")), rs.getInt("cantidad"));
            }
        }

        return facturaProducto;
    }

    @Override
    public List<FacturaProducto> getAll() throws SQLException {
        List<FacturaProducto> facturaProducto = new ArrayList<>();

        String query = "SELECT * FROM factura_producto";
        try (Connection conn = factory.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                facturaProducto.add(new FacturaProducto(servicioFacturas.get(rs.getInt("id_factura")), servicioProductos(rs.getInt("id_producto")), rs.getInt("cantidad")));
            }
        }

        return facturaProducto;
    }
}
