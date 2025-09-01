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
        clienteService.cadastrarCliente(cliente);
        // chama cadastrarCliente da classe ClienteServiceImpl que implementa a interface clienteService
        clienteService.cadastrarCliente(cliente);
    }

    public boolean atualizarCliente(Cliente cliente) {
        // chama atualizarCliente da classe ClienteServiceImpl que implementa a interface clienteService
        return clienteService.atualizarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        // chama listarClientes da classe ClienteServiceImpl que implementa a interface clienteService
        return clienteService.listarClientes();
    }

    public Cliente buscarPorCpf(String cpf) {
        // chama buscarClientePorCpf da classe ClienteServiceImpl que implementa a interface clienteService
        return clienteService.buscarClientePorCpf(cpf);
    }
}

   // public Cliente buscarPorId(int id) {
       // return clienteService.buscarClientePorId(id); }
