package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Pedido;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;


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
    public List<Pedido> buscarPorCliente(String cpfCliente) {
        return pedidos.values().stream()
                .filter(p -> p.getCliente().getCpf().equals(cpfCliente))
                .collect(Collectors.toList()); // cria uma lista com os pedidos
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        return pedidos.get(id);

    }
}

