package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.model.Cliente;
import com.adatech.ecommerce.service.ClienteService;
import com.adatech.ecommerce.service.ClienteServiceImpl;

import java.util.List;

/**
 * Controller para gerenciar as operações relacionadas a Clientes.
 * Faz a ponte entre a View e o Service.
 * TODO:
 *  - No construtor, instanciar o ClienteService.
 *  - Criar métodos para:
 *    - cadastrarCliente(cliente): void
 *    - atualizarCliente(cliente): void
 *    - listarClientes(): List<Cliente>
 *    - buscarClientePorCpf(cpf): Cliente
 *  - Os métodos devem chamar as operações correspondentes no ClienteService.
 */
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteServiceImpl();
    }

    public void cadastrarCliente(Cliente cliente) {
        // TODO: Chamar o serviço para cadastrar o cliente
    }

    public void atualizarCliente(Cliente cliente) {
        // TODO: Chamar o serviço para atualizar o cliente
    }

    public List<Cliente> listarClientes() {
        // TODO: Chamar o serviço para listar os clientes
        return null;
    }

    public Cliente buscarClientePorCpf(String cpf) {
        // TODO: Chamar o serviço para buscar o cliente por CPF
        return null;
    }
}

