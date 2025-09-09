package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Produto;
import java.util.List;

public interface ProdutoService {
    boolean cadastrarProduto(Produto produto);
    boolean atualizarProduto(Produto produto);
    List<Produto> listarProdutos();
    Produto buscarProdutoPorId(int id);
}

