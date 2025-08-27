package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cliente;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Implementação em memória do repositório de Clientes.
 * TODO:
 *  - Implementar os métodos da interface ClienteRepository.
 *  - Utilizar um Map<String, Cliente> para armazenar os clientes, usando o CPF como chave.
 *  - O método salvar deve adicionar um novo cliente ou atualizar um existente.
 *  - O método listarTodos deve retornar uma lista com todos os clientes.
 *  - O método buscarPorId (que será o CPF) deve retornar o cliente correspondente.
 *  - O método buscarPorCpf deve implementar a busca específica por CPF.
 */
public class ClienteRepositoryImpl implements ClienteRepository {
    private static final Map<String, Cliente> clientes = new HashMap<>();

    @Override
    public void salvar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public Cliente buscarPorId(String id) {
        // TODO: Implementar a lógica para buscar cliente por ID (CPF)

    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        // TODO: Implementar a lógica para buscar cliente por CPF
        return null;
    }
}

