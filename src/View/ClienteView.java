package View;

import Controller.ClienteController;
import model.Cliente;
import java.util.Scanner;

public class ClienteView {
    private Scanner scanner;
    private ClienteController controller;

    public ClienteView(ClienteController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== MENU CLIENTES ===");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Buscar Cliente por ID");
            System.out.println("4. Atualizar Cliente");
            System.out.println("5. Excluir Cliente");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarCliente();
                        break;
                    case 2:
                        listarClientes();
                        break;
                    case 3:
                        buscarClientePorId();
                        break;
                    case 4:
                        atualizarCliente();
                        break;
                    case 5:
                        excluirCliente();
                        break;
                    case 0:
                        System.out.println("Voltando ao menu principal...");
                        break;
                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Por favor, digite um número válido.");
                opcao = -1;
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        try {
            System.out.println("\n--- CADASTRAR CLIENTE ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Telefone: ");
            String telefone = scanner.nextLine();

            System.out.print("CPF: ");
            String cpf = scanner.nextLine();

            controller.cadastrarCliente(nome, email, telefone, cpf);
            System.out.println("Cliente cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");
        controller.listarClientes().forEach(System.out::println);
    }

    private void buscarClientePorId() {
        try {
            System.out.print("\nDigite o ID do cliente: ");
            Long id = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            Cliente cliente = controller.buscarClientePorId(id);
            if (cliente != null) {
                System.out.println("Cliente encontrado: " + cliente);
            } else {
                System.out.println("Cliente não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void atualizarCliente() {
        try {
            System.out.print("\nDigite o ID do cliente a ser atualizado: ");
            Long id = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();

            System.out.print("Novo email: ");
            String email = scanner.nextLine();

            System.out.print("Novo telefone: ");
            String telefone = scanner.nextLine();

            controller.atualizarCliente(id, nome, email, telefone);
            System.out.println("Cliente atualizado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar cliente: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void excluirCliente() {
        try {
            System.out.print("\nDigite o ID do cliente a ser excluído: ");
            Long id = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            controller.excluirCliente(id);
            System.out.println("Cliente excluído com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage()); // ✅ e.getMessage()
        }
    }
}
