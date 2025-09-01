package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.ClienteController;
import com.adatech.ecommerce.model.Cliente;

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
        while (true) {
            System.out.println("\n------Menu Clientes------");
            System.out.println("(1) Cadastrar Clientes");
            System.out.println("(2) Atualizar dados de Clientes");
            System.out.println("(3) Listar todos os Clientes");
            System.out.println("(4) Buscar Cliente por CPF");
            System.out.println("(5) Voltar");
            System.out.println("Escolha uma opção acima:");

            String opcao = scanner.nextLine().trim();
            switch (opcao) {
                case "1":
                    cadastrarCliente();
                    break;
                case "2":
                     atualizarCliente();
                     break;
                case "3":
                    listarClientes();
                    break;
                case "4":
                    buscarClientePorCpf();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("\nOpção inválida, tente novamente.");

            }

        }
    }

    private void cadastrarCliente() {
        System.out.println("\nCadastrar Clientes");
        System.out.println("Nome: ");
        String nome= scanner.nextLine().trim();
        System.out.println("CPF: ");
        String cpf= scanner.nextLine().trim();
        System.out.println("Email: ");
        String email= scanner.nextLine().trim();
        System.out.println("Endereço: ");
        String endereco= scanner.nextLine().trim();

     Cliente cliente = new Cliente(nome, cpf, email, endereco);
     clienteController.cadastrarCliente(cliente);
     System.out.println("Cliente cadastrado.");
    }

    private void atualizarCliente() {

        System.out.println("\nAtualizar Cliente");
        System.out.print("CPF do cliente: ");
        String cpf = scanner.nextLine().trim();

        Cliente existente = clienteController.buscarPorCpf(cpf);
        if (existente == null) {
            System.out.println("Cliente não encontrado. Tente novamente.");
             return;
        }

        System.out.print("Novo nome: ");
        String nome = scanner.nextLine().trim();
        if (!nome.isBlank()) existente.setNome(nome);

        System.out.print("Novo email: ");
        String email = scanner.nextLine().trim();
        if (!email.isBlank()) existente.setEmail(email);

        System.out.print("Novo endereço: ");
        String endereco = scanner.nextLine().trim();
        if (!endereco.isBlank()) existente.setEndereco(endereco);

        boolean ok = clienteController.atualizarCliente(existente);
        System.out.println(ok ? "Cliente atualizado com sucesso." : "Não foi possível atualizar os dados.");
        }


    private void listarClientes() {
        // TODO: Implementar a lógica para chamar o controller e exibir a lista
    }

    private void buscarClientePorCpf() {
        // TODO: Implementar a lógica para coletar CPF e chamar o controller
    }
}

