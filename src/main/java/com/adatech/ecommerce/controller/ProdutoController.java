package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.model.Produto;
import com.adatech.ecommerce.service.ProdutoService;
import com.adatech.ecommerce.service.ProdutoServiceImpl;
import java.util.List;

/**
 * Controller para gerenciar as operações relacionadas a Produtos.
 * Faz a ponte entre a View e o Service.
 * TODO:
 *  - No construtor, instanciar o ProdutoService.
 *  - Criar métodos para:
 *    - cadastrarProduto(produto): void
 *    - atualizarProduto(produto): void
 *    - listarProdutos(): List<Produto>
 *    - buscarProdutoPorId(id): Produto
 *  - Os métodos devem chamar as operações correspondentes no ProdutoService.
 */
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController() {
        this.produtoService = new ProdutoServiceImpl();
    }

    public void cadastrarProduto(Produto produto) {
        // TODO: Chamar o serviço para cadastrar o produto
    }

    public void atualizarProduto(Produto produto) {
        // TODO: Chamar o serviço para atualizar o produto
    }

    public List<Produto> listarProdutos() {
        // TODO: Chamar o serviço para listar os produtos
        return null;
    }

   /* public Produto buscarProdutoPorId(int id) {
        // TODO: Chamar o serviço para buscar o produto por ID
        return null;
    }*/
}

