package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.model.Cliente;
import com.adatech.ecommerce.service.ClienteService;
import java.util.List;

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void cadastrarCliente(Cliente cliente) {
        clienteService.cadastrarCliente(cliente);
    }

    public boolean atualizarCliente(Cliente cliente) {
        return clienteService.atualizarCliente(cliente);
    }

    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    public Cliente buscarPorCpf(String cpf) {
        return clienteService.buscarClientePorCpf(cpf);
    }

    public Cliente buscarPorId(int id) {
        return clienteService.buscarClientePorId(id);
    }
}