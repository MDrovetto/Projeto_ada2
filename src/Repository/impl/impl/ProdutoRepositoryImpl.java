package Repository.impl.impl;

import model.Produto;
import Repository.impl.ProdutoRepository;
import java.util.*;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    private Map<Long, Produto> produtos = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Produto salvar(Produto produto) {
        if (produto.getId() == null) {
            produto.setId(currentId++);
        }
        produtos.put(produto.getId(), produto);
        return produto;
    }

    @Override
    public Optional<Produto> buscarPorId(Long id) {
        return Optional.ofNullable(produtos.get(id));
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public void deletar(Long id) {
        produtos.remove(id);
    }
}
