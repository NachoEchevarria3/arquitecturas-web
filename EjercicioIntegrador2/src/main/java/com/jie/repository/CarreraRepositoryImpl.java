package com.jie.repository;

import com.jie.dto.CarreraDto;
import com.jie.model.Carrera;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class CarreraRepositoryImpl implements CarreraRepository {
    private EntityManager em;

    public CarreraRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public void save(Carrera carrera) {
        em.getTransaction().begin();
        em.merge(carrera);
        em.getTransaction().commit();
    }

    @Override
    public Carrera findById(int id) {
        return em.find(Carrera.class, id);
    }

    @Override
    public List<Carrera> findAll() {
        return em.createQuery("SELECT c FROM Carrera c", Carrera.class).getResultList();
    }

    @Override
    public void delete(Carrera carrera) {
        em.getTransaction().begin();
        em.remove(carrera);
        em.getTransaction().commit();
    }

    @Override
    public List<CarreraDto> findAllOrderedByCantidadInscriptos() {
        List<CarreraDto> carreras = new ArrayList<>();
        List<Object[]> resultado = em.createQuery("SELECT c.id, c.nombre, COUNT(c) AS cantidadInscriptos FROM Carrera c JOIN c.estudiantes e GROUP BY c.id HAVING COUNT(c) > 0 ORDER BY cantidadInscriptos DESC", Object[].class).getResultList();

        for (Object[] fila : resultado) {
            int carreraId = (int) fila[0];
            String carreraNombre = (String) fila[1];
            long cantidadInscriptos = (long) fila[2];
            carreras.add(new CarreraDto(carreraId, carreraNombre, (int) cantidadInscriptos));
        }

        return carreras;
    }
}
