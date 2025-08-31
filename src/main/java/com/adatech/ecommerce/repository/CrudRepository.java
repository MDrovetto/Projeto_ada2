package main.java.com.adatech.ecommerce.repository;

import main.java.com.adatech.ecommerce.model.Pedido;

import java.util.List;

/**
 * Interface genérica para operações de CRUD (Create, Read, Update, Delete).
 * @param <T> O tipo da entidade.
 * @param <ID> O tipo do ID da entidade.
 * TODO:
 *  - Definir os métodos:
 *    - salvar(T entidade)
 *    - listarTodos(): List<T>
 *    - buscarPorId(ID id): T
 */
public interface CrudRepository<T, ID> {
    Pedido salvar(T entidade);
    List<T> listarTodos();
    T buscarPorId(ID id);
}

