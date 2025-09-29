package Service.impl;

import model.Cliente;
import Repository.impl.ClienteRepository;
import Service.ClienteService;
import java.util.List;
import java.util.Optional;

public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente cadastrarCliente(String nome, String email, String telefone, String cpf) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório");
        }
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("CPF é obrigatório");
        }

        // Verificar se CPF já existe
        Optional<Cliente> clienteExistente = clienteRepository.buscarPorCpf(cpf);
        if (clienteExistente.isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF");
        }

        Cliente cliente = new Cliente(nome, email, telefone, cpf);
        return clienteRepository.salvar(cliente);
    }

    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.listarTodos();
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.buscarPorId(id);
    }

    @Override
    public Cliente atualizarCliente(Long id, String nome, String email, String telefone) {
        Cliente cliente = clienteRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com ID: " + id));

        if (nome != null && !nome.trim().isEmpty()) {
            cliente.setNome(nome);
        }
        if (email != null) {
            cliente.setEmail(email);
        }
        if (telefone != null) {
            cliente.setTelefone(telefone);
        }

        return clienteRepository.salvar(cliente);
    }

    @Override
    public void excluirCliente(Long id) {
        if (!clienteRepository.buscarPorId(id).isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deletar(id);
    }
}
