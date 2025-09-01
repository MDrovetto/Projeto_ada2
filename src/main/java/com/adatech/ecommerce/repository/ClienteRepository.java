package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cliente;


public interface ClienteRepository extends CrudRepository<Cliente, String> {
    Cliente buscarPorCpf(String cpf);
}

