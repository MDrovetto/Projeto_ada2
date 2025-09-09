package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cliente;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ClienteRepositoryImpl implements ClienteRepository {
    private static final Map<Integer, Cliente> clientes = new HashMap<>();
    private static int proximoId = 1;

    @Override
    //Gerador automático de ID
    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() == 0) {
            cliente.setId(proximoId++);
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        return clientes.get(id);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clientes.values().stream()
                .filter(c -> c.getCpf() != null && c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
}

