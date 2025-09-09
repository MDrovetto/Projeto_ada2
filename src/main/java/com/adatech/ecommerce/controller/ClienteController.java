package com.adatech.ecommerce.controller;

import com.adatech.ecommerce.model.Cliente;
import com.adatech.ecommerce.repository.ClienteRepositoryImpl;
import com.adatech.ecommerce.service.ClienteService;
import com.adatech.ecommerce.service.ClienteServiceImpl;
import java.util.List;

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteServiceImpl(new ClienteRepositoryImpl());
    }
    public void cadastrarCliente(Cliente cliente) {
        clienteService.cadastrarCliente(cliente);
        // chama cadastrarCliente da classe ClienteServiceImpl que implementa a interface clienteService
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
    public Cliente buscarPorId(int id) {
        return clienteService.buscarClientePorId(id);
    }
}