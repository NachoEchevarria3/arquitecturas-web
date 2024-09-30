package com.jie.factory;

import com.jie.model.Carrera;
import com.jie.model.Estudiante;
import com.jie.model.EstudianteCarrera;
import com.jie.repository.*;
import com.jie.util.MySqlHibernateUtil;

import javax.persistence.EntityManager;

// Fabrica concreta que implementa los metodos de la Fabrica Abstracta
public class MySqlRepositoryFactory extends RepositoryFactory {
    private static MySqlRepositoryFactory instance;

    private MySqlRepositoryFactory() {}

    public static MySqlRepositoryFactory getInstance() {
        if (instance == null) {
            instance = new MySqlRepositoryFactory();
        }
        return instance;
    }

    @Override
    public EntityManager getEntityManager() {
        return MySqlHibernateUtil.getEntityManager();
    }

    @Override
    public void closeEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Override
    public Repository<Estudiante> getEstudianteRepository() {
        return new EstudianteRepositoryImpl(this);
    }

    @Override
    public Repository<EstudianteCarrera> getEstudianteCarreraRepository() {
        return new EstudianteCarreraRepositoryImpl(this);
    }

    @Override
    public Repository<Carrera> getCarreraRepository() {
        return new CarreraRepositoryImpl(this);
    }
}
