package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Produto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    protected static final Map<Integer, Produto> produtos = new HashMap<>();
    private static int proximoId = 0;

    @Override
    // Lógica de negócio: se o produto não tem um ID, é um novo produto.
    public Produto salvar(Produto produto) {
        if (produto.getId() == 0) {
            produto.setId(proximoId++); // Atribui um novo ID e incrementa o contador.
        }
        // Usa o metodo put do Map para adicionar ou atualizar o produto com base no seu ID.
        produtos.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values()); // retorna uma cópia dos produtos ignorando as chave (ID)
    }

    @Override
    // Busca o produto no Map usando o ID como chave.
    // Retorna o produto se encontrado ou 'null' se não existir.
    public Produto buscarPorId(Integer id) {
        return produtos.get(id);

    }
}

