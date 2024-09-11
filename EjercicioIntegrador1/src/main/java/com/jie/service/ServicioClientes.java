package com.jie.service;

import com.jie.dao.ClienteDao;
import com.jie.dto.ClienteDto;
import com.jie.factory.Factory;
import com.jie.model.Cliente;

import java.sql.SQLException;
import java.util.List;

public class ServicioClientes {
    private ClienteDao daoClientes;

    public ServicioClientes(Factory factory) {
        this.daoClientes = (ClienteDao) factory.getClienteDao();
    }

    public void add(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo");
        try {
            this.daoClientes.add(cliente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente findById(int id) {
        try {
            return this.daoClientes.get(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> findAll() {
        try {
            return this.daoClientes.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo");
        try {
            this.daoClientes.delete(cliente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Cliente cliente) {
        if (cliente == null) throw new IllegalArgumentException("El cliente no puede ser nulo");
        try {
            this.daoClientes.update(cliente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ClienteDto> facturacionTotalClientes() {
        try {
            return this.daoClientes.facturacionTotalClientes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
