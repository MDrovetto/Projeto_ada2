package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Pedido;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class PedidoRepositoryImpl implements PedidoRepository {

    // Nome do arquivo onde os dados serão persistidos
    private static final String ARQUIVO_DADOS = "pedidos.dat";

    // O mapa que atua como cache em memória
    private final Map<Integer, Pedido> pedidos;

    // Gerador de IDs persistente
    private final AtomicInteger proximoId;

    public PedidoRepositoryImpl() {
        // 1. Carrega os dados do arquivo ao iniciar (Desserialização)
        this.pedidos = carregarDados();

        // 2. Define o próximo ID a partir do maior ID carregado
        if (this.pedidos.isEmpty()) {
            this.proximoId = new AtomicInteger(1);
        } else {
            int maxId = this.pedidos.keySet().stream().max(Integer::compare).orElse(0);
            this.proximoId = new AtomicInteger(maxId + 1);
        }
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        if (pedido.getId() == 0) {
            // Usa o AtomicInteger para gerar e incrementar o ID
            pedido.setId(proximoId.getAndIncrement());
        }

        pedidos.put(pedido.getId(), pedido);

        // 3. Persiste a alteração no arquivo (Serialização)
        salvarDados(pedidos);

        return pedido;
    }

    @Override
    public List<Pedido> listarTodos() {
        return new ArrayList<>(pedidos.values());
    }

    @Override
    public List<Pedido> buscarPorCliente(String cpfCliente) {
        return pedidos.values().stream()
                .filter(p -> p.getCliente() != null && p.getCliente().getCpf() != null && p.getCliente().getCpf().equals(cpfCliente))
                .collect(Collectors.toList());
    }

    @Override
    public Pedido buscarPorId(Integer id) {
        return pedidos.get(id);

    }

    // ----------------------------------------------------------------------
    // MÉTODOS DE PERSISTÊNCIA (SERIALIZAÇÃO / DESSERIALIZAÇÃO)
    // ----------------------------------------------------------------------

    /**
     * Tenta carregar o Map de pedidos do arquivo "pedidos.dat".
     */
    private Map<Integer, Pedido> carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {

            System.out.println("Lendo pedidos do arquivo: " + ARQUIVO_DADOS);
            return (Map<Integer, Pedido>) ois.readObject();

        } catch (FileNotFoundException e) {
            // Se o arquivo não existe (primeira execução), retorna um novo mapa vazio.
            System.out.println("Arquivo de dados de pedidos não encontrado. Iniciando vazio.");
            return new HashMap<>();

        } catch (IOException | ClassNotFoundException e) {
            // Em caso de erro de leitura ou desserialização.
            System.err.println("Erro ao carregar dados de pedidos: " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * Salva o Map de pedidos no arquivo "pedidos.dat".
     */
    private void salvarDados(Map<Integer, Pedido> dados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {

            // Escreve o objeto Map inteiro no arquivo
            oos.writeObject(dados);

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados de pedidos no arquivo: " + e.getMessage());
        }
    }
}