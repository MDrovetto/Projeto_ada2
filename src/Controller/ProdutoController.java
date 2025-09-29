package Controller;

import model.Produto;
import Service.ProdutoService;
import java.math.BigDecimal;
import java.util.List;

public class ProdutoController {
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    public void cadastrarProduto(String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        produtoService.cadastrarProduto(nome, descricao, preco, quantidadeEstoque);
    }

    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    public Produto buscarProdutoPorId(Long id) {
        return produtoService.buscarPorId(id).orElse(null);
    }

    public void atualizarProduto(Long id, String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque) {
        produtoService.atualizarProduto(id, nome, descricao, preco, quantidadeEstoque);
    }

    public void excluirProduto(Long id) {
        produtoService.excluirProduto(id);
    }
}