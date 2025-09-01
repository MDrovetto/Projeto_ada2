package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Cliente;
import java.util.List;

/**
 * Interface para os serviços relacionados a Clientes.
 * Define as operações de negócio para a entidade Cliente.
 * TODO:
 *  - Definir os métodos:
 *    - cadastrarCliente(Cliente cliente): boolean
 *    - atualizarCliente(Cliente cliente): boolean
 *    - listarClientes(): List<Cliente>
 *    - buscarClientePorCpf(String cpf): Cliente
 */
public interface ClienteService {
    boolean cadastrarCliente(Cliente cliente);
    boolean atualizarCliente(Cliente cliente){
     if (cliente == null || cliente.getCpf() == null || cliente.getCpf().isBlank())
         return false;
     if (clienteRepository.buscarPorCpf(cliente.getCpf()) == null)
         return false;
         return clienteRepository.salvar(cliente);
}
    List<Cliente> listarClientes();
    Cliente buscarClientePorCpf(String cpf);
  //  Cliente buscarClientePorId(int id);
}

