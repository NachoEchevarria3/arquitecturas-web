package com.jie.util;

import com.jie.factory.RepositoryFactory;
import com.jie.model.Carrera;
import com.jie.model.Estudiante;
import com.jie.model.EstudianteCarrera;
import com.jie.service.CarreraServicio;
import com.jie.service.EstudianteCarreraServicio;
import com.jie.service.EstudianteServicio;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;

public class HelperCsv {
    private final RepositoryFactory factory;

    public HelperCsv(RepositoryFactory factory) {
        this.factory = factory;
    }

    public void readCsv(String url) {
        String urlBase = "src/main/resources/";
        try {
            CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(urlBase + url));

            if (url.equals("estudiantes.csv")) {
                cargarEstudiantes(parser);
            } else if (url.equals("carreras.csv")) {
                cargarCarreras(parser);
            } else if (url.equals("estudianteCarrera.csv")) {
                cargarEstudianteCarrera(parser);
            } else {
                throw new IllegalArgumentException("No se ha encontrado el archivo");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void cargarEstudiantes(CSVParser parser) {
        EstudianteServicio estudianteServicio = new EstudianteServicio(factory);
        for (CSVRecord record : parser) {
            String dni = record.get("DNI");
            String nombre = record.get("nombre");
            String apellido = record.get("apellido");
            String edad = record.get("edad");
            String genero = record.get("genero");
            String ciudad = record.get("ciudad");
            String numeroLibreta = record.get("LU");
            estudianteServicio.save(new Estudiante(
                    Integer.parseInt(dni),
                    nombre,
                    apellido,
                    Integer.parseInt(edad),
                    genero,
                    ciudad,
                    Integer.parseInt(numeroLibreta)
            ));
        }
    }

    private void cargarCarreras(CSVParser parser) {
        CarreraServicio carreraServicio = new CarreraServicio(factory);
        for (CSVRecord record : parser) {
            String id = record.get("id_carrera");
            String nombre = record.get("carrera");
            String duracion = record.get("duracion");
            carreraServicio.save(new Carrera(
                    Integer.parseInt(id),
                    nombre,
                    Integer.parseInt(duracion)
            ));
        }
    }


    private void cargarEstudianteCarrera(CSVParser parser) {
        EstudianteCarreraServicio estudianteCarreraServicio = new EstudianteCarreraServicio(factory);
        EstudianteServicio estudianteServicio = new EstudianteServicio(factory);
        CarreraServicio carreraServicio = new CarreraServicio(factory);
        for (CSVRecord record : parser) {
            String idEstudiante = record.get("id_estudiante");
            String idCarrera = record.get("id_carrera");
            String inscripcion = record.get("inscripcion");
            String graduacion = record.get("graduacion");
            String antiguedad = record.get("antiguedad");
            Estudiante estudiante = estudianteServicio.findById(Integer.parseInt(idEstudiante));
            Carrera carrera = carreraServicio.findById(Integer.parseInt(idCarrera));
            estudianteCarreraServicio.save(new EstudianteCarrera(
                    estudiante,
                    carrera,
                    Integer.parseInt(inscripcion),
                    Integer.parseInt(graduacion),
                    Integer.parseInt(antiguedad)
            ));
        }
    }
}
