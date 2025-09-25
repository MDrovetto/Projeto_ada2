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
        if (pedido.getId() == 0) {
            pedido.setId(proximoId++);
        }

        pedidos.put(pedido.getId(), pedido);
        return pedido;
    }

    @Override
    public List<Pedido> listarTodos() {
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

