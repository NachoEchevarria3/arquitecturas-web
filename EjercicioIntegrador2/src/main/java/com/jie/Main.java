package com.jie;

import com.jie.factory.RepositoryFactory;
import com.jie.model.Carrera;
import com.jie.service.CarreraServicio;

public class Main {
    public static void main(String[] args) {
        CarreraServicio carreraServicio = new CarreraServicio(RepositoryFactory.getFactory(RepositoryFactory.dbTypes.MYSQL));
        Carrera carrera = carreraServicio.findById(1);
        carreraServicio.findAllEstudiantesByCarreraAndCiudad(carrera, "Mar del Plata").forEach(System.out::println);
    }
}