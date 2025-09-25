package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.ClienteController;
import com.adatech.ecommerce.model.Cliente;
import java.util.List;
import java.util.Scanner;

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
            System.out.println("(5) Buscar Cliente por ID");
            System.out.println("(6) Voltar");
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
                    buscarClientePorId();
                    break;
                case "6":
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

     Cliente cliente = new Cliente(0,nome, cpf, email, endereco);
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
        System.out.println("\n--- Lista de Clientes ---");
        List<Cliente> clientes = clienteController.listarClientes();
        if (clientes.isEmpty()) { // isEmpty verifica se list tem elementos.
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    private void buscarClientePorCpf() {
        System.out.println("\n--- Busca de Cliente por CPF ---");
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        Cliente cliente = clienteController.buscarPorCpf(cpf);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(cliente);
        } else {
            System.err.println("Cliente não encontrado.");
        }
    }

        private void buscarClientePorId() {
            System.out.print("Digite o ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Cliente cliente = clienteController.buscarPorId(id);
            if (cliente != null) {
                System.out.println("Cliente encontrado:");
                System.out.println(cliente);
            } else {
                System.err.println("Cliente não encontrado.");
            }
        }

}

