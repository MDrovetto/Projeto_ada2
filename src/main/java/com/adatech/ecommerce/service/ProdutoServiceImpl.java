package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Produto;
import com.adatech.ecommerce.repository.ProdutoRepository;
import com.adatech.ecommerce.repository.ProdutoRepositoryImpl;

import java.util.List;

/**
 * Implementação dos serviços relacionados a Produtos.
 * Contém a lógica de negócio para a entidade Produto.
 * TODO:
 *  - Implementar os métodos da interface ProdutoService.
 *  - No construtor, instanciar o ProdutoRepository.
 *  - No método cadastrarProduto, validar se o produto já existe antes de salvar.
 *  - No método atualizarProduto, verificar se o produto existe antes de atualizar.
 *  - Os métodos devem interagir com a camada de repositório (ProdutoRepository).
 */
public class ProdutoServiceImpl implements ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl() {
        this.produtoRepository = new ProdutoRepositoryImpl();
    }

    @Override
    public boolean cadastrarProduto(Produto produto) {
        // TODO: Implementar a lógica de cadastro
        // Lógica de negócio: validar se o produto já existe antes de salvar.
        // Se a busca por ID retornar um produto, significa que ele já existe.
        if (produtoRepository.buscarPorId(produto.getId()) != null) {
            System.err.println("Erro: Já existe um produto com este ID.");
            return false;
        }
        // Se não houver conflito de ID, o produto é salvo.
        produtoRepository.salvar(produto);
        return true ;
    }

    @Override
    public boolean atualizarProduto(Produto produto) {
        // TODO: Implementar a lógica de atualização
        // Lógica de negócio: verificar se o produto a ser atualizado existe.
        // Se a busca por ID retornar null, o produto não pode ser atualizado.
        if (produtoRepository.buscarPorId(produto.getId()) == null) {
            System.err.println("Erro: Produto não encontrado para atualização.");
            return false;
        }
        // Se o produto existir, a atualização é realizada.
        produtoRepository.salvar(produto);
        return true;
    }

    @Override
    public List<Produto> listarProdutos() {
        // TODO: Implementar a lógica para listar os produtos
        // Delega a responsabilidade de listar todos os produtos para a camada de repositório.
        return produtoRepository.listarTodos(); // retorna uma cópia do Arraylist
    }

    @Override
    public Produto buscarProdutoPorId(int id) {
        // TODO: Implementar a lógica de busca por ID
        // Delega a responsabilidade de buscar o produto por ID para a camada de repositório.
        return produtoRepository.buscarPorId(id);
    }
}

