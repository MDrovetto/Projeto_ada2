package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Cliente;
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
        // TODO: Implementar a lógica de atualização
        // Lógica de negócio: verificar se o cliente a ser atualizado existe.
    //   Cliente clienteExistente = clienteRepository.buscarPorId(cliente.getCpf());

        if (clienteExistente == null) {
            System.err.println("Erro: Cliente não encontrado para atualização.");
            return false;
        }

        // Lógica de negócio: verificar se o novo CPF já pertence a outro cliente.
        Cliente clienteComNovoCpf = clienteRepository.buscarPorCpf(cliente.getCpf());

        // Se o cliente com o novo CPF existir e for diferente do cliente que estamos atualizando...
        if (clienteComNovoCpf != null && !clienteComNovoCpf.getCpf().equals(cliente.getCpf())) {
            System.err.println("Erro: O novo CPF já está em uso por outro cliente.");
            return false;
        }

        // Se as validações passarem, o cliente é salvo e o método retorna true.
        clienteRepository.salvar(cliente);
        return true;
    }

    @Override
    public List<Cliente> listarClientes() {
        // TODO: Implementar a lógica para listar os clientes
        // O método do repositório já retorna a lista completa.
        return clienteRepository.listarTodos(); // retorna uma copia método implementado na classe clienteRepository
    }

    @Override
    public Cliente buscarClientePorCpf(String cpf) {
        // TODO: Implementar a lógica de busca por CPF
        // O método do repositório já retorna o cliente ou null se não existir.
        return clienteRepository.buscarPorCpf(cpf);
    }

}

