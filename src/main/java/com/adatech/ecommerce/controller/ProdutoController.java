package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.model.Produto;
import com.adatech.ecommerce.service.ProdutoService;
import java.util.List;


public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public void cadastrarProduto(Produto produto) {
        produtoService.cadastrarProduto(produto);
    }

    public void atualizarProduto(Produto produto) {
        produtoService.atualizarProduto(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    public Produto buscarProdutoPorId(int id) {
        return produtoService.buscarProdutoPorId(id);
    }
}

