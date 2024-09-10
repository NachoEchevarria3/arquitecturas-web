package com.jie;

import com.jie.util.HelperCSV;

public class Main {
    public static void main(String[] args) {
        System.out.println("Trabajo Práctico 1 - Arquitecturas Web");
        HelperCSV.readCsv("clientes.csv");
        HelperCSV.readCsv("productos.csv");
        HelperCSV.readCsv("facturas.csv");
    }
}