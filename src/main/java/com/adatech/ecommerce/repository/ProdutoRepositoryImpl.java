package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Produto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    protected static final Map<Integer, Produto> produtos = new HashMap<>();
    private static int proximoId = 1;

    @Override
    public Produto salvar(Produto produto) {
        if (produto.getId() == 1) {
            produto.setId(proximoId++);
        }

        produtos.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public Produto buscarPorId(Integer id) {
        return produtos.get(id);

    }
}

