package com.adatech.ecommerce.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementação em memória do repositório de Produtos.
 * TODO:
 *  - Implementar os métodos da interface ProdutoRepository.
 *  - Utilizar um Map<Integer, Produto> para armazenar os produtos, usando o ID como chave.
 *  - O método salvar deve adicionar um novo produto ou atualizar um existente.
 *  - O método listarTodos deve retornar uma lista com todos os produtos.
 *  - O método buscarPorId deve retornar o produto correspondente.
 */
public class ProdutoRepositoryImpl implements ProdutoRepository {
    private static final Map<Integer, Produto> produtos = new HashMap<>();
    private static int proximoId = 1;

    @Override
    public void salvar(Produto produto) {
        // TODO: Implementar a lógica para salvar ou atualizar o produto
    }

    @Override
    public List<Produto> listarTodos() {
        // TODO: Implementar a lógica para retornar a lista de produtos
        return null;
    }

    @Override
    public Produto buscarPorId(Integer id) {
        // TODO: Implementar a lógica para buscar produto por ID
        return null;
    }
}

