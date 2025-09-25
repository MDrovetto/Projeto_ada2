package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Produto;
import com.adatech.ecommerce.repository.ProdutoRepository;
import java.util.List;
import com.adatech.ecommerce.exception.*;

public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public boolean cadastrarProduto(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new ValidationException("Nome do produto é obrigatório.");
        }
        if (produto.getPreco() == null) {
            throw new ValidationException("Preço é obrigatório.");
        }
        if (produto.getPreco().signum() < 0) {
            throw new ValidationException("Preço não pode ser negativo.");
        }
        if (produto.getEstoque() < 0) {
            throw new ValidationException("Estoque não pode ser negativo.");
        }
        if (produtoRepository.buscarPorId(produto.getId()) != null && produto.getId() != 0) {
            throw new BusinessException("Já existe produto com este ID.");
        }
        produtoRepository.salvar(produto);
        return true;
    }

    @Override
    public boolean atualizarProduto(Produto produto) {
        if (produtoRepository.buscarPorId(produto.getId()) == null) {
            throw new NotFoundException("Produto não encontrado para atualização.");
        }
        produtoRepository.salvar(produto);
        return true;
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoRepository.listarTodos();
    }

    @Override
    public Produto buscarProdutoPorId(int id) {
        return produtoRepository.buscarPorId(id);
    }
}

