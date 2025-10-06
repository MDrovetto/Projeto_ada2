package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.model.Produto;
import com.adatech.ecommerce.service.ProdutoService;
import com.adatech.ecommerce.service.ProdutoServiceImpl;
import java.util.List;

import com.adatech.ecommerce.repository.ProdutoRepositoryImpl;

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

