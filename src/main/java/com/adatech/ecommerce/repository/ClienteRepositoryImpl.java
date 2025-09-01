package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cliente;
import com.adatech.ecommerce.model.Pedido;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class ClienteRepositoryImpl implements ClienteRepository {
    private static final Map<String, Cliente> clientes = new HashMap<>();

    @Override
    public boolean salvar(Cliente cliente) {
        if (cliente == null || cliente.getCpf() == null) 
        return false;
        clientes.put(cliente.getCpf(), cliente);
        return true;
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }
  
   /* @Override
    public Cliente buscarPorId(int id) {
        return clientes.get(id);
    }
    */

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clientes.get(cpf);
    }
}

