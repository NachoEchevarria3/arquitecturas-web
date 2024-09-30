package com.jie.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class MySqlHibernateUtil {
    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("Integrador2");
        } catch (Throwable e) {
            throw new ExceptionInInitializerError("Error al inicializar EntityManagerFactory");
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void closeEmf() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
