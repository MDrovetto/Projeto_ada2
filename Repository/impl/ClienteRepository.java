package Repository.impl;

import model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepository {
    Cliente salvar(Cliente cliente);
    Optional<Cliente> buscarPorId(Long id);
    List<Cliente> listarTodos();
    void deletar(Long id);
    Optional<Cliente> buscarPorCpf(String cpf);
}