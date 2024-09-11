package com.jie.dao;

import com.jie.dto.ClienteDto;
import com.jie.model.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDao extends Dao<Cliente> {
    List<ClienteDto> facturacionTotalClientes() throws SQLException;
}
