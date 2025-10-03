package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cupom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CupomRepositoryImpl implements CupomRepository {

    // Simula o banco de dados
    private final Map<Integer, Cupom> database = new HashMap<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);

    @Override
    public Cupom salvar(Cupom cupom) {
        if (cupom.getId() == 0) {
            // Verifica duplicidade de código antes de salvar
            if (database.values().stream().anyMatch(c -> c.getCodigo().equalsIgnoreCase(cupom.getCodigo()))) {
                // Em um cenário real, lançaríamos uma exceção de persistência
                System.err.println("Erro: Já existe um cupom com o código " + cupom.getCodigo());
                return null;
            }
            int novoId = idGenerator.getAndIncrement();
            cupom.setId(novoId);
        }
        database.put(cupom.getId(), cupom);
        return cupom;
    }

    @Override
    public Cupom buscarPorCodigo(String codigo) {
        return database.values().stream()
                .filter(c -> c.getCodigo().equalsIgnoreCase(codigo))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Cupom buscarPorId(int id) {
        return database.get(id);
    }

    @Override
    public List<Cupom> listarTodos() {
        return new ArrayList<>(database.values());
    }
}