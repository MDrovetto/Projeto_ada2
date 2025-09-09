package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Cliente;
import com.adatech.ecommerce.repository.ClienteRepository;
import com.adatech.ecommerce.repository.ClienteRepositoryImpl;

import java.util.List;

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    public ClienteServiceImpl(ClienteRepositoryImpl clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public boolean cadastrarCliente(Cliente cliente) {
        // Lógica de negócio: validar se o CPF já existe.
        Cliente clienteExistente = clienteRepository.buscarPorCpf(cliente.getCpf()); // buscarPorCpf() metodo classe clienteRepository

        if (clienteExistente != null) {
            // Se o clienteExistente não for nulo, significa que o CPF já está em uso.
            System.err.println("Erro: Já existe um cliente cadastrado com este CPF.");
            return false;
        }

        // Se o CPF for único, o cliente é salvo e o método retorna true.
        clienteRepository.salvar(cliente);
        return true;
    }

    @Override
    public boolean atualizarCliente(Cliente cliente) {
        // Lógica de negócio: verificar se o cliente a ser atualizado existe.
        Cliente clienteExistente = clienteRepository.buscarPorId(cliente.getId());

        if (clienteExistente == null) {
            System.err.println("Erro: Cliente não encontrado para atualização.");
            return false;
        }

        // Lógica de negócio: verificar se o novo CPF já pertence a outro cliente.
        Cliente clienteComMesmoCpf = clienteRepository.buscarPorCpf(cliente.getCpf());

        // Se o cliente com o novo CPF existir e for diferente do cliente que estamos atualizando...
        if (clienteComMesmoCpf != null && clienteComMesmoCpf.getId() != cliente.getId()) {
            System.err.println("Erro: O novo CPF já está em uso por outro cliente.");
            return false;
        }

        // Se as validações passarem, o cliente é salvo e o metodo retorna true.
        clienteRepository.salvar(cliente);
        return true;
    }

    @Override
    public List<Cliente> listarClientes() {
        // O metodo do repositório já retorna a lista completa.
        return clienteRepository.listarTodos(); // retorna uma copia metodo implementado na classe clienteRepository
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) {
        // O metodo do repositório já retorna o cliente ou null se não existir.
        return clienteRepository.buscarPorCpf(cpf);
    }

    @Override
    public Cliente buscarClientePorId(int id) {
        // O metodo do repositório já retorna o cliente ou null se não existir.
        return clienteRepository.buscarPorId(id);
    }

}

