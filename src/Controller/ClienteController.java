package Controller;

import model.Cliente;
import Service.ClienteService;
import java.util.List;

public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public void cadastrarCliente(String nome, String email, String telefone, String cpf) {
        clienteService.cadastrarCliente(nome, email, telefone, cpf);
    }

    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }

    public Cliente buscarClientePorId(Long id) {
        return clienteService.buscarPorId(id).orElse(null);
    }

    public void atualizarCliente(Long id, String nome, String email, String telefone) {
        clienteService.atualizarCliente(id, nome, email, telefone);
    }

    public void excluirCliente(Long id) {
        clienteService.excluirCliente(id);
    }
}