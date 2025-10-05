package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Produto;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdutoRepositoryImpl implements ProdutoRepository {

    // Nome do arquivo onde os dados serão persistidos
    private static final String ARQUIVO_DADOS = "produtos.dat";

    // O mapa que atua como cache em memória (agora não estático)
    private final Map<Integer, Produto> produtos;

    // Gerador de IDs persistente
    private final AtomicInteger proximoId;

    public ProdutoRepositoryImpl() {
        // 1. Carrega os dados do arquivo ao iniciar (Desserialização)
        this.produtos = carregarDados();

        // 2. Define o próximo ID a partir do maior ID carregado
        if (this.produtos.isEmpty()) {
            this.proximoId = new AtomicInteger(1);
        } else {
            int maxId = this.produtos.keySet().stream().max(Integer::compare).orElse(0);
            this.proximoId = new AtomicInteger(maxId + 1);
        }
    }

    @Override
    public Produto salvar(Produto produto) {
        // CORREÇÃO E GERAÇÃO DE ID: Assumimos que ID 0 significa novo produto.
        // O código original verificava 'produto.getId() == 1', o que estava incorreto.
        if (produto.getId() == 0) {
            // Usa o AtomicInteger para gerar e incrementar o ID
            produto.setId(proximoId.getAndIncrement());
        }

        produtos.put(produto.getId(), produto);

        // 3. Persiste a alteração no arquivo (Serialização)
        salvarDados(produtos);

        return produto;
    }

    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos.values());
    }

    @Override
    public Produto buscarPorId(Integer id) {
        return produtos.get(id);
    }

    // ----------------------------------------------------------------------
    // MÉTODOS DE PERSISTÊNCIA (SERIALIZAÇÃO / DESSERIALIZAÇÃO)
    // ----------------------------------------------------------------------

    /**
     * Tenta carregar o Map de produtos do arquivo "produtos.dat".
     */
    private Map<Integer, Produto> carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {

            System.out.println("Lendo produtos do arquivo: " + ARQUIVO_DADOS);
            return (Map<Integer, Produto>) ois.readObject();

        } catch (FileNotFoundException e) {
            // Se o arquivo não existe (primeira execução), retorna um novo mapa vazio.
            System.out.println("Arquivo de dados de produtos não encontrado. Iniciando vazio.");
            return new HashMap<>();

        } catch (IOException | ClassNotFoundException e) {
            // Em caso de erro de leitura ou desserialização.
            System.err.println("Erro ao carregar dados de produtos: " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * Salva o Map de produtos no arquivo "produtos.dat".
     */
    private void salvarDados(Map<Integer, Produto> dados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {

            // Escreve o objeto Map inteiro no arquivo
            oos.writeObject(dados);

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de produtos no arquivo: " + e.getMessage());
        }
    }
}