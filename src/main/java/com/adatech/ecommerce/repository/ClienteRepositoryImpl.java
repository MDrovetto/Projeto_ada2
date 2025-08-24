package com.adatech.ecommerce.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        // TODO: Implementar a lógica para salvar ou atualizar o cliente
    }

    @Override
    public List<Cliente> listarTodos() {
        // TODO: Implementar a lógica para retornar a lista de clientes
        return null;
    }

    @Override
    public Cliente buscarPorId(String id) {
        // TODO: Implementar a lógica para buscar cliente por ID (CPF)
        return null;
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        // TODO: Implementar a lógica para buscar cliente por CPF
        return null;
    }
}

