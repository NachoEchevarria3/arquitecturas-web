package com.jie;

import com.jie.factory.Factory;
import com.jie.service.ServicioClientes;
import com.jie.util.HelperCSV;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trabajo Práctico 1 - Arquitecturas Web");
        // HelperCSV helperCSV = new HelperCSV(Factory.getFactory(Factory.dbTypes.MYSQL));
        ServicioClientes servicioClientes = new ServicioClientes(Factory.getFactory(Factory.dbTypes.MYSQL));
        servicioClientes.findAll().forEach(System.out::println);
    }
}