package com.jie;

import com.jie.factory.RepositoryFactory;
import com.jie.service.CarreraServicio;

public class Main {
    public static void main(String[] args) {
        // HelperCsv cargarDB = new HelperCsv(RepositoryFactory.getFactory(RepositoryFactory.dbTypes.MYSQL));
        // cargarDB.readCsv("carreras.csv");
        // cargarDB.readCsv("estudiantes.csv");
        // cargarDB.readCsv("estudianteCarrera.csv");
        CarreraServicio carreraServicio = new CarreraServicio(RepositoryFactory.getFactory(RepositoryFactory.dbTypes.MYSQL));
        carreraServicio.generarReporteCarreras().forEach(System.out::println);
    }
}