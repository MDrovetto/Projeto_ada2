package Repository.impl.impl;

import model.Pedido;
import Repository.impl.PedidoRepository;
import java.util.*;

public class PedidoRepositoryImpl implements PedidoRepository {
    private Map<Long, Pedido> pedidos = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Pedido salvar(Pedido pedido) {
        if (pedido.getId() == null) {
            pedido.setId(currentId++);
        }
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public void deletar(Long id) {
        pedidos.remove(id);
    }
}
