package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Cliente;
import java.util.List;

public interface ClienteService {

    boolean cadastrarCliente(Cliente cliente);
    boolean atualizarCliente(Cliente cliente);
    List<Cliente> listarClientes();
    Cliente buscarClientePorCpf(String cpf);
    Cliente buscarClientePorId(int id);
}

