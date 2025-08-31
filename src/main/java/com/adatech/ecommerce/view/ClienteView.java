package main.java.com.adatech.ecommerce.view;

import main.java.com.adatech.ecommerce.controller.ClienteController;
import java.util.Scanner;

/**
 * View para interação com o usuário para o gerenciamento de Clientes.
 * TODO:
 *  - No construtor, receber o ClienteController.
 *  - Criar um método para exibir o menu de clientes (Cadastrar, Atualizar, Listar, Buscar por CPF, Voltar).
 *  - Criar métodos para cada uma das opções do menu, que irão:
 *    - Solicitar os dados necessários ao usuário (nome, cpf, email, etc.).
 *    - Chamar o método correspondente no ClienteController.
 *    - Exibir o resultado da operação para o usuário (sucesso, erro, dados do cliente, etc.).
 */
public class ClienteView {

    private final ClienteController clienteController;
    private final Scanner scanner;

    public ClienteView(ClienteController clienteController, Scanner scanner) {
        this.clienteController = clienteController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        // TODO: Implementar o loop do menu de clientes
    }

    private void cadastrarCliente() {
        // TODO: Implementar a lógica para coletar dados e chamar o controller
    }

    private void atualizarCliente() {
        // TODO: Implementar a lógica para coletar dados e chamar o controller
    }

    private void listarClientes() {
        // TODO: Implementar a lógica para chamar o controller e exibir a lista
    }

    private void buscarClientePorCpf() {
        // TODO: Implementar a lógica para coletar CPF e chamar o controller
    }
}

