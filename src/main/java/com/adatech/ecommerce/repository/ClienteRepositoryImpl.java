package main.java.com.adatech.ecommerce.repository;

import main.java.com.adatech.ecommerce.model.Cliente;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;


public class ClienteRepositoryImpl implements ClienteRepository {
    private static final Map<String, Cliente> clientes = new HashMap<>();

    @Override
    public boolean salvar(Cliente cliente) {
        if (cliente != null && cliente.getCpf() != null) {
            clientes.put(cliente.getCpf(), cliente);
            return true;
        }
        return false;
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public Cliente buscarPorId(String id) {
        return clientes.get(id);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clientes.get(cpf);
    }
}

