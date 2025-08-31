package main.java.com.adatech.ecommerce.repository;

import java.util.List;

public interface CrudRepository<T, ID> {
    boolean salvar(T entidade);
    List<T> listarTodos();
    T buscarPorId(ID id);
}

