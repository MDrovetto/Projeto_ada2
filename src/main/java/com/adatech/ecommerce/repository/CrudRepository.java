package com.adatech.ecommerce.repository;

import main.java.com.adatech.ecommerce.model.Pedido;

import java.util.List;

public interface CrudRepository<T, ID> {

    Pedido salvar(T entidade);
    List<T> listarTodos();
    T buscarPorId(ID id);
}

