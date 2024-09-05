package com.jie;

import com.jie.dao.ClienteDaoImpl;
import com.jie.dao.Dao;
import com.jie.model.Cliente;

public class Main {
    public static void main(String[] args) {
        Dao<Cliente> clienteDao = new ClienteDaoImpl();

        try {
            clienteDao.createTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}