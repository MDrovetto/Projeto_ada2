package Service;

import model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteService {
    Cliente cadastrarCliente(String nome, String email, String telefone, String cpf);
    List<Cliente> listarClientes();
    Optional<Cliente> buscarPorId(Long id);
    Cliente atualizarCliente(Long id, String nome, String email, String telefone);
    void excluirCliente(Long id);
}