package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Pedido;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;


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
    public Pedido salvar(Pedido pedido) {
        // TODO: Implementar a lógica para salvar ou atualizar o
        // Lógica de negócio: se o pedido não tem um ID, é um novo pedido.
        if (pedido.getId() == 0) {
            // Atribui um novo ID e incrementa o contador.
            pedido.setId(proximoId++);
        }
        // Usa o método put do Map para adicionar ou atualizar o pedido com base no seu ID.
        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    @Override
    public List<Pedido> listarTodos() {
        // TODO: Implementar a lógica para retornar a lista de pedidos
        // Retorna uma nova lista com todos os valores (os objetos Pedido) do Map.
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        // TODO: Implementar a lógica para buscar pedido por ID
        // Busca o pedido no Map usando o ID como chave.
        // O método get() do Map retorna o objeto ou 'null' se a chave não for encontrada.
        // É importante que o código que chamar este método trate a possibilidade de um retorno null.
        return pedidos.get(id);

    }
}

