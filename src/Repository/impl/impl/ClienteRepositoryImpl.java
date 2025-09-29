package Repository.impl.impl;

import model.Cliente;
import Repository.impl.ClienteRepository;
import java.util.*;

public class ClienteRepositoryImpl implements ClienteRepository {
    private Map<Long, Cliente> clientes = new HashMap<>();
    private Long currentId = 1L;

    @Override
    public Cliente salvar(Cliente cliente) {
        if (cliente.getId() == null) {
            cliente.setId(currentId++);
        }
        clientes.put(cliente.getId(), cliente);
        return cliente;
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return Optional.ofNullable(clientes.get(id));
    }

    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes.values());
    }

    @Override
    public void deletar(Long id) {
        clientes.remove(id);
    }

    @Override
    public Optional<Cliente> buscarPorCpf(String cpf) {
        return clientes.values().stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst();
    }
}