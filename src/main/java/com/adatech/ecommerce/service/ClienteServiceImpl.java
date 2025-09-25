package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Cliente;
import com.adatech.ecommerce.repository.ClienteRepository;
import com.adatech.ecommerce.exception.*;

import java.util.List;

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public boolean cadastrarCliente(Cliente cliente) {
        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new ValidationException("CPF é obrigatório.");
        }
        if (clienteRepository.buscarPorCpf(cliente.getCpf()) != null) {
            throw new BusinessException("Já existe um cliente com este CPF.");
        }
        clienteRepository.salvar(cliente);
        return true;
    }

    @Override
    public boolean atualizarCliente(Cliente cliente) {
        Cliente existente = clienteRepository.buscarPorId(cliente.getId());
        if (existente == null) {
            throw new NotFoundException("Cliente não encontrado para atualização.");
        }
        Cliente comMesmoCpf = clienteRepository.buscarPorCpf(cliente.getCpf());
        if (comMesmoCpf != null && comMesmoCpf.getId() != cliente.getId()) {
            throw new BusinessException("O CPF informado pertence a outro cliente.");
        }
        clienteRepository.salvar(cliente);
        return true;
    }

@Override
public List<Cliente> listarClientes() {
    return clienteRepository.listarTodos();
}

@Override
public Cliente buscarClientePorCpf(String cpf) {
    return clienteRepository.buscarPorCpf(cpf);
}

@Override
public Cliente buscarClientePorId(int id) {
    return clienteRepository.buscarPorId(id);
}

}

