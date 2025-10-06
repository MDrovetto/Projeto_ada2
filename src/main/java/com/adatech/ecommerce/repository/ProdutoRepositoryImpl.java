package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Produto;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdutoRepositoryImpl implements ProdutoRepository {

    private static final String ARQUIVO_DADOS = "produtos.dat";

    private final Map<Integer, Produto> produtos;

    private final AtomicInteger proximoId;

    public ProdutoRepositoryImpl() {
        this.produtos = carregarDados();

        if (this.produtos.isEmpty()) {
            this.proximoId = new AtomicInteger(1);
        } else {
            int maxId = this.produtos.keySet().stream().max(Integer::compare).orElse(0);
            this.proximoId = new AtomicInteger(maxId + 1);
        }
    }

    @Override
    public Produto salvar(Produto produto) {
        if (produto.getId() == 0) {
            produto.setId(proximoId.getAndIncrement());
        }

        produtos.put(produto.getId(), produto);

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

    private Map<Integer, Produto> carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {

            System.out.println("Lendo produtos do arquivo: " + ARQUIVO_DADOS);
            return (Map<Integer, Produto>) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados de produtos não encontrado. Iniciando vazio.");
            return new HashMap<>();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados de produtos: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private void salvarDados(Map<Integer, Produto> dados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(dados);

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de produtos no arquivo: " + e.getMessage());
        }
    }
}