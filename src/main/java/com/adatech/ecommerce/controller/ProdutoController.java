package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.model.Produto;
import com.adatech.ecommerce.service.ProdutoService;
import com.adatech.ecommerce.service.ProdutoServiceImpl;
import java.util.List;

public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController() {
        this.produtoService = new ProdutoServiceImpl();
    }

    public void cadastrarProduto(Produto produto) {
        // chama cadastrarProduto da classe ProdutoServiceImpl que implementa a interface ProdutoService
        produtoService.cadastrarProduto(produto);
    }

    public void atualizarProduto(Produto produto) {
        // chama atualizarProduto da classe ProdutoServiceImpl que implementa a interface ProdutoService
        produtoService.atualizarProduto(produto);
    }

    public List<Produto> listarProdutos() {
        // chama listarProdutos() da classe ProdutoServiceImpl que implementa a interface ProdutoService
        return produtoService.listarProdutos();
    }

    public Produto buscarProdutoPorId(int id) {
        // chama buscarProdutoPorId da classe ProdutoServiceImpl que implementa a interface ProdutoService
        return produtoService.buscarProdutoPorId(id);
    }
    
}

