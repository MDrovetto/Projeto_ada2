package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cliente;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteRepositoryImpl implements ClienteRepository {

    // Nome do arquivo onde os dados serão persistidos
    private static final String ARQUIVO_DADOS = "clientes.dat";

    // O mapa que atua como cache em memória (antes era estático, agora é final)
    private final Map<Integer, Cliente> clientes;

    // Usamos AtomicInteger para gerenciar IDs de forma thread-safe e persistente
    private final AtomicInteger proximoId;

    public ClienteRepositoryImpl() {
        // 1. Carrega os dados do arquivo ao iniciar a aplicação (desserialização)
        this.clientes = carregarDados();

        // 2. Define o próximo ID a partir do maior ID carregado
        if (this.clientes.isEmpty()) {
            this.proximoId = new AtomicInteger(1);
        } else {
            int maxId = this.clientes.keySet().stream().max(Integer::compare).orElse(0);
            this.proximoId = new AtomicInteger(maxId + 1);
        }
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() == 0) {
            // Usa o AtomicInteger para gerar e incrementar o ID
            cliente.setId(proximoId.getAndIncrement());
        }
        clientes.put(cliente.getId(), cliente);

        // 3. Persiste a alteração no arquivo (serialização)
        salvarDados(clientes);

        return cliente;
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public Cliente buscarPorId(Integer id) {
        return clientes.get(id);
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clientes.values().stream()
                .filter(c -> c.getCpf() != null && c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    // ----------------------------------------------------------------------
    // MÉTODOS DE PERSISTÊNCIA (SERIALIZAÇÃO / DESSERIALIZAÇÃO)
    // ----------------------------------------------------------------------

    /**
     * Tenta carregar o Map de clientes do arquivo "clientes.dat".
     */
    private Map<Integer, Cliente> carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {

            // Lê o objeto Map inteiro do arquivo
            System.out.println("Lendo clientes do arquivo: " + ARQUIVO_DADOS);
            return (Map<Integer, Cliente>) ois.readObject();

        } catch (FileNotFoundException e) {
            // Se o arquivo não existe (primeira execução), retorna um novo mapa vazio.
            System.out.println("Arquivo de dados não encontrado. Iniciando com clientes vazios.");
            return new HashMap<>();

        } catch (IOException | ClassNotFoundException e) {
            // Em caso de erro de leitura ou desserialização, loga o erro e retorna vazio.
            System.err.println("Erro ao carregar dados do arquivo: " + e.getMessage());
            return new HashMap<>();
        }
    }

    /**
     * Salva o Map de clientes no arquivo "clientes.dat".
     */
    private void salvarDados(Map<Integer, Cliente> dados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {

            // Escreve o objeto Map inteiro no arquivo
            oos.writeObject(dados);

        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo: " + e.getMessage());
        }
    }
}