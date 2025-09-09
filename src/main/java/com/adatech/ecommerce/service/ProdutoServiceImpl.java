package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Produto;
import com.adatech.ecommerce.repository.ProdutoRepository;
import com.adatech.ecommerce.repository.ProdutoRepositoryImpl;
import java.util.List;

public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl() {
        this.produtoRepository = new ProdutoRepositoryImpl();
    }

    @Override
    // Lógica de negócio: validar se o produto já existe antes de salvar
    // Se a busca por ID retornar um produto, significa que ele já existe
    public boolean cadastrarProduto(Produto produto) {
        if (produtoRepository.buscarPorId(produto.getId()) != null) {
            System.err.println("Erro: Já existe um produto com este ID.");
            return false;
        }
        // Se não houver conflito de ID, o produto é salvo.
        produtoRepository.salvar(produto);
        return true ;
    }

    @Override
    // Lógica de negócio: verificar se o produto a ser atualizado existe.
    // Se a busca por ID retornar null, o produto não pode ser atualizado.
    public boolean atualizarProduto(Produto produto) {
        if (produtoRepository.buscarPorId(produto.getId()) == null) {
            System.err.println("Erro: Produto não encontrado para atualização.");
            return false;
        }
        // Se o produto existir, a atualização é realizada.
        produtoRepository.salvar(produto);
        return true;
    }

    @Override
    // Delega a responsabilidade de listar todos os produtos para a camada de repositório.
    public List<Produto> listarProdutos() {
        return produtoRepository.listarTodos(); // retorna uma cópia do Arraylist
    }

    @Override
    // Delega a responsabilidade de buscar o produto por ID para a camada de repositório.
    public Produto buscarProdutoPorId(int id) {
        return produtoRepository.buscarPorId(id);
    }
}

