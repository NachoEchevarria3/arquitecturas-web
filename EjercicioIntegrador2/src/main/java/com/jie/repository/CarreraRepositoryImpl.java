package com.jie.repository;

import com.jie.dto.CarreraDto;
import com.jie.factory.RepositoryFactory;
import com.jie.model.Carrera;
import com.jie.model.Estudiante;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository {
    private RepositoryFactory repositoryFactory;

    public CarreraRepositoryImpl(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public void save(Carrera carrera) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(carrera);
            em.getTransaction().commit();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public Carrera findById(int id) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public List<Carrera> findAll() {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            return em.createQuery("SELECT c FROM Carrera c", Carrera.class).getResultList();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public void delete(Carrera carrera) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public List<CarreraDto> findAllOrderedByCantidadInscriptos() {
        List<CarreraDto> carreras = new ArrayList<>();
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            List<Object[]> resultado = em.createQuery("SELECT c.id, c.nombre, COUNT(c) AS cantidadInscriptos FROM Carrera c JOIN c.estudiantes e GROUP BY c.id HAVING COUNT(c) > 0 ORDER BY cantidadInscriptos DESC", Object[].class).getResultList();

            for (Object[] fila : resultado) {
                int carreraId = (int) fila[0];
                String carreraNombre = (String) fila[1];
                long cantidadInscriptos = (long) fila[2];
                carreras.add(new CarreraDto(carreraId, carreraNombre, (int) cantidadInscriptos));
            }

            return carreras;
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }

    @Override
    public List<Estudiante> findAllEstudiantesByCarreraAndCiudad(Carrera carrera, String ciudad) {
        EntityManager em = repositoryFactory.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Estudiante e JOIN e.carreras ec WHERE ec.carrera = :carrera AND e.ciudadResidencia = :ciudad")
                    .setParameter("carrera", carrera)
                    .setParameter("ciudad", ciudad)
                    .getResultList();
        } finally {
            repositoryFactory.closeEntityManager(em);
        }
    }
}
