package com.jie;

import com.jie.dao.ClienteDaoImpl;
import com.jie.dao.FacturaDaoImpl;
import com.jie.dao.FacturaProductoDaoImpl;
import com.jie.dao.ProductoDaoImpl;
import com.jie.factory.Factory;
import com.jie.service.ServicioClientes;
import com.jie.service.ServicioProductos;
import com.jie.util.HelperCSV;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trabajo Práctico 1 - Arquitecturas Web");
        // HelperCSV helperCSV = new HelperCSV(Factory.getFactory(Factory.dbTypes.MYSQL));
        cargarDB();
        ServicioClientes servicioClientes = new ServicioClientes(Factory.getFactory(Factory.dbTypes.MYSQL));
        servicioClientes.facturacionTotalClientes().forEach(System.out::println);
        ServicioProductos servicioProductos = new ServicioProductos(Factory.getFactory(Factory.dbTypes.MYSQL));
        System.out.println(servicioProductos.obtenerProductoQueMasRecaudo());

    }

    private static void cargarDB(){
        try {
        new ClienteDaoImpl(Factory.getFactory(Factory.dbTypes.MYSQL)).createTable();
        new ProductoDaoImpl(Factory.getFactory(Factory.dbTypes.MYSQL)).createTable();
        new FacturaDaoImpl(Factory.getFactory(Factory.dbTypes.MYSQL)).createTable();
        new FacturaProductoDaoImpl(Factory.getFactory(Factory.dbTypes.MYSQL)).createTable();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        HelperCSV helperCSV = new HelperCSV(Factory.getFactory(Factory.dbTypes.MYSQL));
        helperCSV.readCsv("clientes.csv");
        helperCSV.readCsv("productos.csv");
        helperCSV.readCsv("facturas.csv");
        helperCSV.readCsv("facturas-productos.csv");
    }
}