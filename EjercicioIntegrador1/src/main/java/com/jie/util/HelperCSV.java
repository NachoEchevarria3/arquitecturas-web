package com.jie.util;

import com.jie.model.Cliente;
import com.jie.model.Factura;
import com.jie.model.FacturaProducto;
import com.jie.model.Producto;
import com.jie.service.ServicioClientes;
import com.jie.service.ServicioFacturas;
import com.jie.service.ServicioFacturasProductos;
import com.jie.service.ServicioProductos;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;

public class HelperCSV {
    public static void readCsv(String url) {
        String urlBase = "src/main/resources/";
        try {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(urlBase + url));

            if (url.equals("clientes.csv")) {
                cargarClientes(parser);
            } else if(url.equals("facturas.csv")) {
                cargarFacturas(parser);
            } else if(url.equals("facturas-productos.csv")) {
                cargarFacturasProductos(parser);
            } else if (url.equals("productos.csv")) {
                cargarProductos(parser);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void cargarProductos(CSVParser parser) {
        ServicioProductos servicioProductos = new ServicioProductos();
        for (CSVRecord csvRecord : parser) {
            String id = csvRecord.get("idProducto");
            String nombre = csvRecord.get("nombre");
            String valor = csvRecord.get("valor");
            servicioProductos.add(new Producto(Integer.parseInt(id), nombre, Float.parseFloat(valor)));
        }
    }

    private static void cargarFacturasProductos(CSVParser parser) {
        ServicioFacturasProductos servicioFacturasProductos = new ServicioFacturasProductos();
        ServicioFacturas servicioFacturas = new ServicioFacturas();
        ServicioProductos servicioProductos = new ServicioProductos();

        for (CSVRecord csvRecord : parser) {
            String idFactura = csvRecord.get("idFactura");
            String idProducto = csvRecord.get("idProducto");
            String cantidad = csvRecord.get("cantidad");
            Factura factura = servicioFacturas.findById(Integer.parseInt(idFactura));
            Producto producto = servicioProductos.findById(Integer.parseInt(idProducto));
            servicioFacturasProductos.add(new FacturaProducto(factura, producto, Integer.parseInt(cantidad)));
        }
    }

    private static void cargarFacturas(CSVParser parser) {
        ServicioFacturas servicioFacturas = new ServicioFacturas();
        ServicioClientes servicioClientes = new ServicioClientes();

        for (CSVRecord csvRecord : parser) {
            String idFactura = csvRecord.get("idFactura");
            String idCliente = csvRecord.get("idCliente");
            Cliente cliente = servicioClientes.findById(Integer.parseInt(idCliente));
            servicioFacturas.add(new Factura(Integer.parseInt(idFactura), cliente));
        }
    }

    private static void cargarClientes(CSVParser parser) {
        ServicioClientes servicioClientes = new ServicioClientes();
        for (CSVRecord csvRecord : parser) {
            String id = csvRecord.get("idCliente");
            String nombre = csvRecord.get("nombre");
            String email = csvRecord.get("email");
            servicioClientes.add(new Cliente(Integer.parseInt(id), nombre, email));
        }
    }
}
