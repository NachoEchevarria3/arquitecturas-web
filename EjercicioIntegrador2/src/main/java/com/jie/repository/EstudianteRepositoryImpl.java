package com.jie.repository;

import com.jie.model.Estudiante;

import javax.persistence.EntityManager;
import java.util.List;

public class EstudianteRepositoryImpl implements EstudianteRepository {
    private EntityManager em;

    public EstudianteRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Estudiante estudiante) {
        em.getTransaction().begin();
        em.merge(estudiante);
        em.getTransaction().commit();
    }

    @Override
    public Estudiante findById(int id) {
        return em.find(Estudiante.class, id);
    }

    @Override
    public List<Estudiante> findAll() {
        return em.createQuery("SELECT e FROM Estudiante e", Estudiante.class).getResultList();
    }

    @Override
    public void delete(Estudiante estudiante) {
        em.getTransaction().begin();
        em.remove(estudiante);
        em.getTransaction().commit();
    }

    @Override
    public List<Estudiante> findAllOrderedByEdad() {
        return em.createQuery("SELECT e FROM Estudiante e ORDER BY e.edad DESC", Estudiante.class).getResultList();
    }

    @Override
    public Estudiante findByNumeroLibreta(int numeroLibreta) {
        return (Estudiante) em.createQuery("SELECT e FROM Estudiante e WHERE e.numeroLibreta = :numeroLibreta")
                .setParameter("numeroLibreta", numeroLibreta)
                .getSingleResult();
    }

    @Override
    public List<Estudiante> findAllByGenero(String genero) {
        return em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = :genero").setParameter("genero", genero).getResultList();
    }
}
