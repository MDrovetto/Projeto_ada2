package com.adatech.ecommerce.service;

import com.adatech.ecommerce.repository.ClienteRepository;
import com.adatech.ecommerce.repository.ClienteRepositoryImpl;

import java.util.List;

/**
 * Implementação dos serviços relacionados a Clientes.
 * Contém a lógica de negócio para a entidade Cliente.
 * TODO:
 *  - Implementar os métodos da interface ClienteService.
 *  - No construtor, instanciar o ClienteRepository.
 *  - No método cadastrarCliente, validar se o CPF já existe antes de salvar.
 *  - No método atualizarCliente, verificar se o cliente existe antes de atualizar.
 *  - Os métodos devem interagir com a camada de repositório (ClienteRepository).
 */
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl() {
        this.clienteRepository = new ClienteRepositoryImpl();
    }

    @Override
    public boolean cadastrarCliente(Cliente cliente) {
        // TODO: Implementar a lógica de cadastro
        return false;
    }

    @Override
    public boolean atualizarCliente(Cliente cliente) {
        // TODO: Implementar a lógica de atualização
        return false;
    }

    @Override
    public List<Cliente> listarClientes() {
        // TODO: Implementar a lógica para listar os clientes
        return null;
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) {
        // TODO: Implementar a lógica de busca por CPF
        return null;
    }
}

