package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cupom;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CupomRepositoryImpl implements CupomRepository {

    private static final String ARQUIVO_DADOS = "cupons.dat";
    private final Map<Integer, Cupom> database;
    private final AtomicInteger idGenerator;

    public CupomRepositoryImpl() {
        this.database = carregarDados();
        if (this.database.isEmpty()) {
            this.idGenerator = new AtomicInteger(1);
        } else {
            int maxId = this.database.keySet().stream().max(Integer::compare).orElse(0);
            this.idGenerator = new AtomicInteger(maxId + 1);
        }
    }

    @Override
    public Cupom salvar(Cupom cupom) {
        if (cupom.getId() == 0) {
            if (database.values().stream().anyMatch(c -> c.getCodigo().equalsIgnoreCase(cupom.getCodigo()))) {
                System.err.println("Erro: Já existe um cupom com o código " + cupom.getCodigo());
                return null;
            }
            int novoId = idGenerator.getAndIncrement();
            cupom.setId(novoId);
        }
        database.put(cupom.getId(), cupom);
        salvarDados(database);

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

    private Map<Integer, Cupom> carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {

            System.out.println("Lendo cupons do arquivo: " + ARQUIVO_DADOS);
            return (Map<Integer, Cupom>) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados de cupons não encontrado. Iniciando vazio.");
            return new HashMap<>();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados de cupons: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private void salvarDados(Map<Integer, Cupom> dados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(dados);

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de cupons no arquivo: " + e.getMessage());
        }
    }
}