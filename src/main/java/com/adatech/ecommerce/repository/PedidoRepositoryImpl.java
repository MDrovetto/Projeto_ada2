package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Pedido;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementação em memória do repositório de Pedidos.
 * TODO:
 *  - Implementar os métodos da interface PedidoRepository.
 *  - Utilizar um Map<Integer, Pedido> para armazenar os pedidos, usando o ID como chave.
 *  - O método salvar deve adicionar um novo pedido ou atualizar um existente.
 *  - O método listarTodos deve retornar uma lista com todos os pedidos.
 *  - O método buscarPorId deve retornar o pedido correspondente.
 */
public class PedidoRepositoryImpl implements PedidoRepository {
    private static final Map<Integer, Pedido> pedidos = new HashMap<>();
    private static int proximoId = 1;

    @Override
    public void salvar(Pedido pedido) {
        // TODO: Implementar a lógica para salvar ou atualizar o pedido
    }

    @Override
    public List<Pedido> listarTodos() {
        // TODO: Implementar a lógica para retornar a lista de pedidos
        return null;
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        // TODO: Implementar a lógica para buscar pedido por ID
        return null;
    }
}

