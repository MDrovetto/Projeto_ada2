package main.java.com.adatech.ecommerce.view;

import main.java.com.adatech.ecommerce.controller.ClienteController;
import main.java.com.adatech.ecommerce.model.Cliente;

import java.util.List;
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
        int opcao;
        do {
            System.out.println("\n--- Menu de Clientes ---");
            System.out.println("1. Cadastrar Cliente");
            System.out.println("2. Atualizar Cliente");
            System.out.println("3. Listar Clientes");
            System.out.println("4. Buscar Cliente por CPF");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarCliente();
                    break;
                case 2:
                    atualizarCliente();
                    break;
                case 3:
                    listarClientes();
                    break;
                case 4:
                    buscarClientePorCpf();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        // TODO: Implementar a lógica para coletar dados e chamar o controller
        System.out.println("\n--- Cadastro de Cliente ---");
        // O ID é gerado automaticamente, então não é necessário solicitar.
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite o email: ");
        String email = scanner.nextLine();
        System.out.print("Digite o endereço: ");
        String endereco = scanner.nextLine();

        // Cria o objeto Cliente usando o construtor com todos os parâmetros.
        // O ID é passado como 0, pois será gerado pela camada de repositório.
        Cliente cliente = new Cliente(0, nome, cpf, email, endereco);

        clienteController.cadastrarCliente(cliente);
        System.out.println("Cliente cadastrado com sucesso!");
    }

    private void atualizarCliente() {
        // TODO: Implementar a lógica para coletar dados e chamar o
        System.out.println("\n--- Atualização de Cliente ---");
        System.out.print("Digite o CPF do cliente que deseja atualizar: ");
        String cpf = scanner.nextLine();

        // 1. Busca o cliente existente para obter os dados atuais.
        Cliente clienteExistente = clienteController.buscarClientePorCpf(cpf);

        // 2. Verifica se o cliente foi encontrado antes de prosseguir.
        if (clienteExistente == null) {
            System.err.println("Erro: Cliente não encontrado para atualização.");
            return;
        }

        // 3. Exibe os dados atuais e solicita os novos dados.
        System.out.println("Cliente encontrado. Dados atuais:");
        System.out.println("Nome: " + clienteExistente.getNome());
        System.out.println("Email: " + clienteExistente.getEmail());
        System.out.println("Endereço: " + clienteExistente.getEndereco());

        System.out.println("\nDigite as novas informações (ou pressione Enter para manter a atual):");

        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine();
        if (novoNome.isBlank()) { // isBlack() método da classe String retorna true se a String for nula ou estiver vazia
            novoNome = clienteExistente.getNome(); // Mantém o nome atual
        }

        System.out.print("Novo email: ");
        String novoEmail = scanner.nextLine();
        if (novoEmail.isBlank()) {
            novoEmail = clienteExistente.getEmail(); // Mantém o email atual
        }

        System.out.print("Novo endereço: ");
        String novoEndereco = scanner.nextLine();
        if (novoEndereco.isBlank()) {
            novoEndereco = clienteExistente.getEndereco(); // Mantém o endereço atual
        }

        // 4. Cria um novo objeto Cliente com os dados atualizados e o ID original.
        Cliente clienteAtualizado = new Cliente(
                clienteExistente.getId(),
                novoNome,
                cpf,
                novoEmail,
                novoEndereco
        );

        // 5. Chama o controller para salvar as alterações.
        clienteController.atualizarCliente(clienteAtualizado);
        System.out.println("Cliente atualizado com sucesso!");
    }

    private void listarClientes() {
        // TODO: Implementar a lógica para chamar o controller e exibir a lista
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
        // TODO: Implementar a lógica para coletar CPF e chamar o controller
        System.out.println("\n--- Busca de Cliente por CPF ---");
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        Cliente cliente = clienteController.buscarClientePorCpf(cpf);
        if (cliente != null) {
            System.out.println("Cliente encontrado:");
            System.out.println(cliente);
        } else {
            System.err.println("Cliente não encontrado.");
        }

    }
}

