package com.jie.factory;

import com.jie.model.Carrera;
import com.jie.model.Estudiante;
import com.jie.model.EstudianteCarrera;
import com.jie.repository.Repository;

import javax.persistence.EntityManager;

// Fabrica Abstracta, proporciona metodos para obtener los diferentes repositorios.
public abstract class RepositoryFactory {
    public enum dbTypes {
        MYSQL
    }

    public abstract EntityManager getEntityManager();
    public abstract void closeEntityManager(EntityManager em);

    // Define Familia de productos
    public abstract Repository<Estudiante> getEstudianteRepository();
    public abstract Repository<EstudianteCarrera> getEstudianteCarreraRepository();
    public abstract Repository<Carrera> getCarreraRepository();

    // Metodo que devuelve una factory concreta segun el tipo de persistencia
    public static RepositoryFactory getFactory(dbTypes dbType) {
        switch (dbType) {
            case MYSQL:
                return MySqlRepositoryFactory.getInstance();
            default:
                throw new IllegalArgumentException("Unsupported dbType: " + dbType);
        }
    }
}
