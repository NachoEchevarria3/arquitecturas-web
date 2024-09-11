package com.jie;

import com.jie.factory.Factory;
import com.jie.service.ServicioClientes;
import com.jie.service.ServicioProductos;
import com.jie.util.HelperCSV;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trabajo Práctico 1 - Arquitecturas Web");
        // HelperCSV helperCSV = new HelperCSV(Factory.getFactory(Factory.dbTypes.MYSQL));
        ServicioClientes servicioClientes = new ServicioClientes(Factory.getFactory(Factory.dbTypes.MYSQL));
        servicioClientes.facturacionTotalClientes().forEach(System.out::println);
        ServicioProductos servicioProductos = new ServicioProductos(Factory.getFactory(Factory.dbTypes.MYSQL));
        System.out.println(servicioProductos.obtenerProductoQueMasRecaudo());

    }

    private void prepopulateDB(){
        ClienteDaoImpl clientes = new ClienteDaoImpl(Factory.getFactory(Factory.dbTypes.MYSQL));
        clientes.createTable();
        ProductoDaoImpl pd = new ProductoDaoImpl(Factory.getFactory(Factory.dbTypes.MYSQL));
        pd.createTable();
        FacturaDaoImpl ffdao = new FacturaDaoImpl(Factory.getFactory(Factory.dbTypes.MYSQL));
        ffdao.createTable();
        FacturaProductoDaoImpl facturas = new FacturaProductoDaoImpl(Factory.getFactory(Factory.dbTypes.MYSQL));
        facturas.createTable();

        HelperCSV helperCSV = new HelperCSV(Factory.getFactory(Factory.dbTypes.MYSQL));
        helperCSV.readCsv("clientes.csv");
        helperCSV.readCsv("productos.csv");
        helperCSV.readCsv("facturas.csv");
        helperCSV.readCsv("facturas-productos.csv");
    }
}