package main.java.com.adatech.ecommerce.repository;

import main.java.com.adatech.ecommerce.model.Cliente;

/**
 * Interface específica para o repositório de Clientes.
 * Herda as operações básicas de CrudRepository.
 * TODO:
 *  - Adicionar, se necessário, métodos específicos para busca de clientes,
 *    como buscarPorCpf(String cpf).
 */
public interface ClienteRepository extends com.adatech.ecommerce.repository.CrudRepository<Cliente, String> {
    Cliente buscarPorCpf(String cpf);
}

