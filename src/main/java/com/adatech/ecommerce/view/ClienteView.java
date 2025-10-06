package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.ClienteController;
import com.adatech.ecommerce.exception.BusinessException;
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
                    System.err.println("\nOpção inválida: '" + opcao + "'. Por favor, digite apenas o número da opção.");
            }
        }
    }

    private void cadastrarCliente() {
        String nome = "";
        String cpf = "";
        String email = "";
        String endereco = "";
        boolean cancelado = false;

        while (true) {
            System.out.println("\n--- Cadastrar Novo Cliente ---");
            System.out.println("Digite 0 para voltar ao menu");
            System.out.print("Nome: ");
            nome = scanner.nextLine().trim();

            if (nome.equalsIgnoreCase("0")) {
                cancelado = true;
                break;
            }
            if (nome.isEmpty()) {
                System.err.println("Digite um nome válido por favor.");
            } else if (!nome.matches("[a-zA-Z\\s]+")) {
                System.err.println("O nome deve conter apenas letras e espaços.");
            } else {
                break;
            }
        }

        if (!cancelado) {
            while (true) {
                System.out.print("CPF 11 dígitos: ");
                cpf = scanner.nextLine().trim();
                if (cpf.equalsIgnoreCase("0")) {
                    cancelado = true;
                    break;
                }
                if (!cpf.matches("\\d{11}")) {
                    System.err.println("CPF inválido. Deve conter 11 números. Por favor, tente novamente.");
                } else {
                    break;
                }
            }
        }

        if (!cancelado) {
            while (true) {
                System.out.print("Email: ");
                email = scanner.nextLine().trim();
                if (email.equalsIgnoreCase("0")) {
                    cancelado = true;
                    break;
                }
                String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
                if (!email.matches(emailRegex)) {
                    System.err.println("Formato de e-mail inválido. Por favor, tente novamente.");
                } else {
                    break;
                }
            }
        }

        if (!cancelado) {
            while (true) {
                System.out.print("Endereço: ");
                endereco = scanner.nextLine().trim();
                if (endereco.equalsIgnoreCase("0")) {
                    cancelado = true;
                    break;
                }
                if (endereco.isEmpty()) {
                    System.err.println("Endereço inválido. Por favor, tente novamente.");
                } else {
                    break;
                }
            }
        }

        if (!cancelado) {
            try {
                Cliente cliente = new Cliente(0, nome, cpf, email, endereco);
                clienteController.cadastrarCliente(cliente);
                System.out.println("\nCliente cadastrado com sucesso!");
            } catch (BusinessException ex) {
                System.err.println("\nEsse CPF já existe. " + ex.getMessage());
                System.out.println("Por favor, verifique os dados e comece novamente.");
            } catch (Exception ex) {
                System.err.println("\nOcorreu um erro inesperado: " + ex.getMessage());
            }
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