package main.java.com.adatech.ecommerce.repository;

import main.java.com.adatech.ecommerce.model.Produto;

/**
 * Interface específica para o repositório de Produtos.
 * Herda as operações básicas de CrudRepository.
 * TODO:
 *  - Adicionar, se necessário, métodos específicos para busca de produtos.
 */
public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
}

