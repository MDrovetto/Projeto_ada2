package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cupom;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CupomRepositoryImpl implements CupomRepository {

    // Nome do arquivo onde os dados serão persistidos
    private static final String ARQUIVO_DADOS = "cupons.dat";

    // O mapa que atua como cache em memória
    private final Map<Integer, Cupom> database;

    // Gerador de IDs persistente
    private final AtomicInteger idGenerator;

    public CupomRepositoryImpl() {
        // 1. Carrega os dados do arquivo ao iniciar (Desserialização)
        this.database = carregarDados();

        // 2. Define o próximo ID a partir do maior ID carregado
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
            // Verifica duplicidade de código antes de gerar novo ID
            if (database.values().stream().anyMatch(c -> c.getCodigo().equalsIgnoreCase(cupom.getCodigo()))) {
                System.err.println("Erro: Já existe um cupom com o código " + cupom.getCodigo());
                return null;
            }
            // Usa o AtomicInteger para gerar e incrementar o ID
            int novoId = idGenerator.getAndIncrement();
            cupom.setId(novoId);
        }
        database.put(cupom.getId(), cupom);

        // 3. Persiste a alteração no arquivo (Serialização)
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

    // ----------------------------------------------------------------------
    // MÉTODOS DE PERSISTÊNCIA (SERIALIZAÇÃO / DESSERIALIZAÇÃO)
    // ----------------------------------------------------------------------

    /**
     * Tenta carregar o Map de cupons do arquivo "cupons.dat".
     */
    private Map<Integer, Cupom> carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {

            System.out.println("Lendo cupons do arquivo: " + ARQUIVO_DADOS);
            return (Map<Integer, Cupom>) ois.readObject();

        } catch (FileNotFoundException e) {
            // Se o arquivo não existe (primeira execução), retorna um novo mapa vazio.
            System.out.println("Arquivo de dados de cupons não encontrado. Iniciando vazio.");
            return new HashMap<>();

        } catch (IOException | ClassNotFoundException e) {
            // Em caso de erro de leitura ou desserialização.
            System.err.println("Erro ao carregar dados de cupons: " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * Salva o Map de cupons no arquivo "cupons.dat".
     */
    private void salvarDados(Map<Integer, Cupom> dados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {

            // Escreve o objeto Map inteiro no arquivo
            oos.writeObject(dados);

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de cupons no arquivo: " + e.getMessage());
        }
    }
}