package Service;

import model.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProdutoService {
    Produto cadastrarProduto(String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque);
    List<Produto> listarProdutos();
    Optional<Produto> buscarPorId(Long id);
    Produto atualizarProduto(Long id, String nome, String descricao, BigDecimal preco, Integer quantidadeEstoque);
    void excluirProduto(Long id);
}