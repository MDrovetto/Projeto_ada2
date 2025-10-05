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

            // Leitura da opção como String (scanner.nextLine()) para robustez
            String opcao = scanner.nextLine().trim();

            // Não precisamos de try/catch aqui, pois o switch já lida com Strings
            // e o default pega qualquer valor inválido.
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
                    buscarClientePorId(); // Método refatorado para ter try/catch no parse
                    break;
                case "6":
                    return;
                default:
                    System.err.println("\nOpção inválida: '" + opcao + "'. Por favor, digite apenas o número da opção.");
            }
        }
    }

    private void cadastrarCliente() {
        System.out.println("\n--- Cadastrar Clientes ---");

        // Todas as entradas já são Strings, o que é seguro.
        System.out.print("Nome: ");
        String nome= scanner.nextLine().trim();
        System.out.print("CPF: ");
        String cpf= scanner.nextLine().trim();
        System.out.print("Email: ");
        String email= scanner.nextLine().trim();
        System.out.print("Endereço: ");
        String endereco= scanner.nextLine().trim();

        // Tratamento de exceção em caso de erro na criação ou cadastro (simulando um erro de domínio)
        try {
            Cliente cliente = new Cliente(0, nome, cpf, email, endereco);
            clienteController.cadastrarCliente(cliente);
            System.out.println("Cliente cadastrado com sucesso!");
        } catch (Exception ex) {
            System.err.println("Falha ao cadastrar o cliente. Detalhe: " + ex.getMessage());
        }
    }

    private void atualizarCliente() {

        System.out.println("\n--- Atualizar Cliente ---");
        System.out.print("CPF do cliente para busca: ");
        String cpf = scanner.nextLine().trim();

        Cliente existente = clienteController.buscarPorCpf(cpf);
        if (existente == null) {
            System.err.println("Cliente com CPF '" + cpf + "' não encontrado.");
            return;
        }

        System.out.println("Cliente encontrado. Deixe o campo em branco para manter o valor atual.");

        System.out.print("Novo nome (" + existente.getNome() + "): ");
        String nome = scanner.nextLine().trim();
        if (!nome.isBlank()) existente.setNome(nome);

        System.out.print("Novo email (" + existente.getEmail() + "): ");
        String email = scanner.nextLine().trim();
        if (!email.isBlank()) existente.setEmail(email);

        System.out.print("Novo endereço (" + existente.getEndereco() + "): ");
        String endereco = scanner.nextLine().trim();
        if (!endereco.isBlank()) existente.setEndereco(endereco);

        // Tratamento de exceção em caso de erro na atualização (simulando um erro de domínio)
        try {
            boolean ok = clienteController.atualizarCliente(existente);
            System.out.println(ok ? "Cliente atualizado com sucesso." : "Não foi possível atualizar os dados (verifique a lógica no Controller).");
        } catch (Exception ex) {
            System.err.println("Falha ao atualizar o cliente. Detalhe: " + ex.getMessage());
        }
    }

    private void listarClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        // Tratamento de exceção em caso de erro na busca ou conexão
        try {
            List<Cliente> clientes = clienteController.listarClientes();
            if (clientes.isEmpty()) {
                System.out.println("Nenhum cliente cadastrado.");
            } else {
                for (Cliente cliente : clientes) {
                    System.out.println(cliente);
                }
            }
        } catch (Exception ex) {
            System.err.println("Falha ao listar os clientes. Detalhe: " + ex.getMessage());
        }
    }

    private void buscarClientePorCpf() {
        System.out.println("\n--- Busca de Cliente por CPF ---");
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine().trim();

        // Tratamento de exceção em caso de erro na busca
        try {
            Cliente cliente = clienteController.buscarPorCpf(cpf);
            if (cliente != null) {
                System.out.println("Cliente encontrado:");
                System.out.println(cliente);
            } else {
                System.err.println("Cliente com CPF '" + cpf + "' não encontrado.");
            }
        } catch (Exception ex) {
            System.err.println("Falha ao buscar o cliente. Detalhe: " + ex.getMessage());
        }
    }

    // *** MÉTODO MELHORADO COM nextLine() + parse COM try/catch ***
    private void buscarClientePorId() {
        System.out.println("\n--- Busca de Cliente por ID ---");
        System.out.print("Digite o ID (apenas números): ");

        // 1. Leitura sempre como String
        String idText = scanner.nextLine().trim();

        try {
            // 2. Tentativa de Conversão (Parse)
            int id = Integer.parseInt(idText);

            // 3. Chamada ao Controller com tratamento de exceção
            Cliente cliente = clienteController.buscarPorId(id);
            if (cliente != null) {
                System.out.println("Cliente encontrado:");
                System.out.println(cliente);
            } else {
                System.err.println("Cliente com ID " + id + " não encontrado.");
            }
        } catch (NumberFormatException ex) {
            // 4. Captura e Mensagem Amigável se a entrada não for um número
            System.err.println("Erro: ID inválido. O ID deve ser um número inteiro (Ex: 5). Detalhe: " + ex.getMessage());
        } catch (Exception ex) {
            // Captura qualquer outro erro que possa vir do Controller/Sistema
            System.err.println("Falha ao buscar o cliente por ID. Detalhe: " + ex.getMessage());
        }
    }
}