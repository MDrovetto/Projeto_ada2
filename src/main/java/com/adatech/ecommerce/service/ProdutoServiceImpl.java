package main.java.com.adatech.ecommerce.service;

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
        return false;
    }

    @Override
    public boolean atualizarProduto(Produto produto) {
        // TODO: Implementar a lógica de atualização
        return false;
    }

    @Override
    public List<Produto> listarProdutos() {
        // TODO: Implementar a lógica para listar os produtos
        return null;
    }

    @Override
    public Produto buscarProdutoPorId(int id) {
        // TODO: Implementar a lógica de busca por ID
        return null;
    }
}

