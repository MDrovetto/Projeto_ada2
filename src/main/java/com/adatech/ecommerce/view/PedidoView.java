package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.PedidoController;
import com.adatech.ecommerce.model.Pedido;

import java.util.List;
import java.util.Scanner;

public class PedidoView {

    private final PedidoController pedidoController;
    private final Scanner scanner;

    public PedidoView(PedidoController pedidoController, Scanner scanner) {
        this.pedidoController = pedidoController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        //loop do menu de pedidos
        int opcao;
        do {
            System.out.println("\n--- Menu de Pedidos ---");
            System.out.println("1. Criar Novo Pedido");
            System.out.println("2. Gerenciar Pedido Existente");
            System.out.println("3. Listar Pedidos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            switch (opcao) {
                case 1:
                    criarPedido();
                    break;
                case 2:
                    gerenciarPedido();
                    break;
                case 3:
                    listarPedidos();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void criarPedido() {
        //lógica para coletar CPF do cliente e chamar o controller
        System.out.println("\n--- Criar Pedido ---");
        System.out.print("Digite o CPF do cliente para o novo pedido: ");
        String cpfCliente = scanner.nextLine();

        Pedido novoPedido = pedidoController.criarPedido(cpfCliente);
        if (novoPedido != null) {
            System.out.println("Pedido criado com sucesso! ID do pedido: " + novoPedido.getId());
        } else {
            System.err.println("Falha ao criar o pedido. Verifique se o cliente existe.");
        }

    }

    private void gerenciarPedido() {
        //lógica para selecionar um pedido e exibir o submenu de gerenciamento
        System.out.println("\n--- Gerenciar Pedido Existente ---");
        System.out.print("Digite o ID do pedido que deseja gerenciar: ");
        int idPedido = scanner.nextInt();
        scanner.nextLine(); // Consome a quebra de linha

        Pedido pedido = pedidoController.buscarPedidoPorId(idPedido);
        if (pedido == null) {
            System.err.println("Erro: Pedido não encontrado.");
            return;
        }

        int opcao;
        do {
            System.out.println("\n--- Pedido ID: " + pedido.getId() + " | Status: " + pedido.getStatus() + " ---");
            System.out.println("1. Adicionar Item");
            System.out.println("2. Remover Item");
            System.out.println("3. Alterar Quantidade do Item");
            System.out.println("4. Finalizar Pedido");
            System.out.println("5. Pagar Pedido");
            System.out.println("6. Entregar Pedido");
            System.out.println("0. Voltar ao menu de pedidos");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            switch (opcao) {
                case 1:
                    // Coleta os dados do novo item
                    System.out.print("Digite o ID do produto: ");
                    int produtoId = scanner.nextInt();
                    System.out.print("Digite a quantidade: ");
                    int quantidade = scanner.nextInt();
                    System.out.print("Digite o preço de venda: ");
                    double precoVenda = scanner.nextDouble();
                    scanner.nextLine(); // Consome a quebra de linha

                    // Agora, a chamada passa todos os parâmetros necessários
                    pedidoController.adicionarItem(pedido.getId(), produtoId, quantidade, precoVenda);
                    break;
                case 2:
                    // Coleta o ID do produto a ser removido
                    System.out.print("Digite o ID do produto a ser removido: ");
                    int produtoIdRemover = scanner.nextInt();
                    scanner.nextLine();

                    pedidoController.removerItem(pedido.getId(), produtoIdRemover);
                    break;
                case 3:
                    // Coleta o ID do produto e a nova quantidade
                    System.out.print("Digite o ID do produto a ser alterado: ");
                    int produtoIdAlterar = scanner.nextInt();
                    System.out.print("Digite a nova quantidade: ");
                    int novaQuantidade = scanner.nextInt();
                    scanner.nextLine();

                    pedidoController.alterarQuantidadeItem(pedido.getId(), produtoIdAlterar, novaQuantidade);
                    break;
                case 4:
                    pedidoController.finalizarPedido(pedido.getId());
                    break;
                case 5:
                    pedidoController.pagarPedido(pedido.getId());
                    break;
                case 6:
                    pedidoController.entregarPedido(pedido.getId());
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void listarPedidos() {
        //lógica para chamar o controller e exibir a lista de pedidos
        System.out.println("\n--- Lista de Pedidos ---");
        List<Pedido> pedidos = pedidoController.listarPedidos();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido cadastrado.");
        } else {
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
                System.out.println("Valor Total: " + pedido.getValorTotal());
                System.out.println("--------------------");
            }
        }

    }
}

