package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Produto;

import java.util.ArrayList;
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
    private static int proximoId = 0; // mudei para zero se ñ precisaria de mais um if no metodo salvar()

    @Override
    public void salvar(Produto produto) {
        // TODO: Implementar a lógica para salvar ou atualizar o produto
        // Lógica de negócio: se o produto não tem um ID, é um novo produto.
        if (produto.getId() == 0) { // na classe produto procura pelo ID caso ñ exista retorna 0 que retorna true = produto novo
            // Atribui um novo ID e incrementa o contador.
            produto.setId(proximoId++);
        }
        // Usa o método put do Map para adicionar ou atualizar o produto com base no seu ID.
        produtos.put(produto.getId(), produto);
    }

    @Override
    public List<Produto> listarTodos() {
        // TODO: Implementar a lógica para retornar a lista de produtos
        // Retorna uma nova lista com todos os valores do Map.
        // Isso evita que a coleção interna seja modificada por quem usa este método.
        return new ArrayList<>(produtos.values()); // retorna uma cópia dos produtos ignorando as chave (ID)
    }

    @Override
    public Produto buscarPorId(Integer id) {
        // TODO: Implementar a lógica para buscar produto por ID
        // Busca o produto no Map usando o ID como chave.
        // Retorna o produto se encontrado ou 'null' se não existir.
        return produtos.get(id); // metodo get() do Map<>()

    }
}

