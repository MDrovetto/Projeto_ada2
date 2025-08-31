package main.java.com.adatech.ecommerce.service;

import java.util.List;

/**
 * Interface para os serviços relacionados a Produtos.
 * Define as operações de negócio para a entidade Produto.
 * TODO:
 *  - Definir os métodos:
 *    - cadastrarProduto(Produto produto): boolean
 *    - atualizarProduto(Produto produto): boolean
 *    - listarProdutos(): List<Produto>
 *    - buscarProdutoPorId(int id): Produto
 */
public interface ProdutoService {
    boolean cadastrarProduto(Produto produto);
    boolean atualizarProduto(Produto produto);
    List<Produto> listarProdutos();
    Produto buscarProdutoPorId(int id);
}

