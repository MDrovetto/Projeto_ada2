package Service.impl;

import model.Produto;
import Repository.impl.ProdutoRepository;
import Service.ProdutoService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ProdutoServiceImpl implements ProdutoService {
    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public Produto cadastrarProduto(String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (preco == null || preco.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Preço deve ser maior que zero");
        }
        if (quantidadeEstoque == null || quantidadeEstoque < 0) {
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");
        }

        Produto produto = new Produto(nome, descricao, preco, quantidadeEstoque);
        return produtoRepository.salvar(produto);
    }

    @Override
    public List<Produto> listarProdutos() {
        return produtoRepository.listarTodos();
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.buscarPorId(id);
    }

    @Override
    public Produto atualizarProduto(Long id, String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        Produto produto = produtoRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado com ID: " + id));

        if (nome != null && !nome.trim().isEmpty()) {
            produto.setNome(nome);
        }
        if (descricao != null) {
            produto.setDescricao(descricao);
        }
        if (preco != null && preco.compareTo(BigDecimal.ZERO) > 0) {
            produto.setPreco(preco);
        }
        if (quantidadeEstoque != null && quantidadeEstoque >= 0) {
            produto.setQuantidadeEstoque(quantidadeEstoque);
        }

        return produtoRepository.salvar(produto);
    }

    @Override
    public void excluirProduto(Long id) {
        if (!produtoRepository.buscarPorId(id).isPresent()) {
            throw new IllegalArgumentException("Produto não encontrado com ID: " + id);
        }
        produtoRepository.deletar(id);
    }
}
