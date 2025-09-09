package com.adatech.ecommerce.repository;

import java.util.List;

public interface CrudRepository<T, ID> {

    T salvar(T entidade);
    List<T> listarTodos();
    T buscarPorId(ID id);
}

