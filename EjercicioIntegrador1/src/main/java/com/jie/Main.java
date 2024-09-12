package com.jie;

import com.jie.factory.Factory;
import com.jie.service.ServicioClientes;
import com.jie.service.ServicioFacturas;
import com.jie.service.ServicioFacturasProductos;
import com.jie.service.ServicioProductos;
import com.jie.util.HelperCSV;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trabajo Práctico 1 - Arquitecturas Web");
        cargarDB();
        ServicioClientes servicioClientes = new ServicioClientes(Factory.getFactory(Factory.dbTypes.MYSQL));
        servicioClientes.facturacionTotalClientes().forEach(System.out::println);
        ServicioProductos servicioProductos = new ServicioProductos(Factory.getFactory(Factory.dbTypes.MYSQL));
        System.out.println(servicioProductos.obtenerProductoQueMasRecaudo());
    }

    private static void cargarDB(){
        ServicioClientes servicioClientes = new ServicioClientes(Factory.getFactory(Factory.dbTypes.MYSQL));
        ServicioProductos servicioProductos = new ServicioProductos(Factory.getFactory(Factory.dbTypes.MYSQL));
        ServicioFacturas servicioFacturas = new ServicioFacturas(Factory.getFactory(Factory.dbTypes.MYSQL));
        ServicioFacturasProductos servicioFacturasProductos = new ServicioFacturasProductos(Factory.getFactory(Factory.dbTypes.MYSQL));

        servicioClientes.createTable();
        servicioProductos.createTable();
        servicioFacturas.createTable();
        servicioFacturasProductos.createTable();

        HelperCSV helperCSV = new HelperCSV(Factory.getFactory(Factory.dbTypes.MYSQL));
        helperCSV.readCsv("clientes.csv");
        helperCSV.readCsv("productos.csv");
        helperCSV.readCsv("facturas.csv");
        helperCSV.readCsv("facturas-productos.csv");
    }
}