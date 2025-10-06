package com.adatech.ecommerce.repository;

import com.adatech.ecommerce.model.Cliente;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteRepositoryImpl implements ClienteRepository {
    private static final String ARQUIVO_DADOS = "clientes.dat";
    private final Map<Integer, Cliente> clientes;
    private final AtomicInteger proximoId;

    public ClienteRepositoryImpl() {
        this.clientes = carregarDados();

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
            cliente.setId(proximoId.getAndIncrement());
        }
        clientes.put(cliente.getId(), cliente);
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

    private Map<Integer, Cliente> carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {

            System.out.println("Lendo clientes do arquivo: " + ARQUIVO_DADOS);
            return (Map<Integer, Cliente>) ois.readObject();

        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de dados não encontrado. Iniciando com clientes vazios.");
            return new HashMap<>();

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados do arquivo: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private void salvarDados(Map<Integer, Cliente> dados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(dados);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados no arquivo: " + e.getMessage());
        }
    }
}