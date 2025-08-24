package com.adatech.ecommerce.repository;

/**
 * Interface específica para o repositório de Clientes.
 * Herda as operações básicas de CrudRepository.
 * TODO:
 *  - Adicionar, se necessário, métodos específicos para busca de clientes,
 *    como buscarPorCpf(String cpf).
 */
public interface ClienteRepository extends CrudRepository<Cliente, String> {
    Cliente buscarPorCpf(String cpf);
}

