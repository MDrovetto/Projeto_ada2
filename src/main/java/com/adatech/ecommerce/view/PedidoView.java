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
        int opcao;
        do {
            System.out.println("\n--- Menu de Pedidos ---");
            System.out.println("1. Criar Novo Pedido");
            System.out.println("2. Gerenciar Pedido Existente");
            System.out.println("3. Listar Pedidos");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            // Refatoração: Usando nextLine() + parse com try/catch para a opção
            String entrada = scanner.nextLine().trim();
            opcao = -1; // Valor padrão para inválido

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                System.err.println("Entrada inválida. Por favor, digite apenas o número da opção.");
                // continue; // Não precisa de continue, o switch lida com o default
            }

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
                    if (opcao != -1) { // Evita repetir a mensagem de erro já dada pelo catch
                        System.err.println("Opção inválida. Tente novamente.");
                    }
            }
        } while (opcao != 0);
    }

    private void criarPedido() {
        System.out.println("\n--- Criar Pedido ---");
        System.out.print("Digite o CPF do cliente para o novo pedido: ");
        String cpfCliente = scanner.nextLine().trim();

        try {
            Pedido novoPedido = pedidoController.criarPedido(cpfCliente);
            if (novoPedido != null) {
                System.out.println("Pedido criado com sucesso! ID do pedido: " + novoPedido.getId());
            } else {
                System.err.println("Falha ao criar o pedido. Verifique se o cliente existe ou se há outro erro.");
            }
        } catch (Exception ex) {
            System.err.println("Erro ao criar pedido. Detalhe: " + ex.getMessage());
        }
    }

    private void gerenciarPedido() {
        System.out.println("\n--- Gerenciar Pedido Existente ---");
        System.out.print("Digite o ID do pedido que deseja gerenciar: ");

        int idPedido;
        try {
            // Leitura segura do ID
            idPedido = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.err.println("Erro: ID de pedido inválido. Detalhe: " + ex.getMessage());
            return;
        }

        Pedido pedido = pedidoController.buscarPedidoPorId(idPedido);
        if (pedido == null) {
            System.err.println("Erro: Pedido ID " + idPedido + " não encontrado.");
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

            String entrada = scanner.nextLine().trim();
            opcao = -1;

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                System.err.println("Entrada inválida. Por favor, digite apenas o número da opção.");
            }

            try {
                switch (opcao) {
                    case 1:
                        adicionarItemAoPedido(pedido.getId());
                        break;
                    case 2:
                        removerItemDoPedido(pedido.getId());
                        break;
                    case 3:
                        alterarQuantidadeItemDoPedido(pedido.getId());
                        break;
                    case 4:
                        pedidoController.finalizarPedido(pedido.getId());
                        System.out.println("Pedido finalizado.");
                        break;
                    case 5:
                        pedidoController.pagarPedido(pedido.getId());
                        System.out.println("Pedido pago.");
                        break;
                    case 6:
                        pedidoController.entregarPedido(pedido.getId());
                        System.out.println("Pedido entregue.");
                        break;
                    case 0:
                        System.out.println("Voltando...");
                        break;
                    default:
                        if (opcao != -1) {
                            System.err.println("Opção inválida. Tente novamente.");
                        }
                }
            } catch (Exception ex) {
                System.err.println("Erro na operação do pedido. Detalhe: " + ex.getMessage());
            }

        } while (opcao != 0);
    }

    // Método auxiliar para Adicionar Item (isolando a lógica de leitura segura)
    private void adicionarItemAoPedido(int pedidoId) {
        System.out.print("Digite o ID do produto: ");
        String produtoIdText = scanner.nextLine().trim();
        System.out.print("Digite a quantidade: ");
        String quantidadeText = scanner.nextLine().trim();
        System.out.print("Digite o preço de venda (Ex: 10.50): ");
        String precoVendaText = scanner.nextLine().trim().replace(",", "."); // Aceita vírgula ou ponto

        try {
            int produtoId = Integer.parseInt(produtoIdText);
            int quantidade = Integer.parseInt(quantidadeText);
            // double foi mantido para compatibilidade, mas BigDecimal seria melhor em um sistema financeiro real.
            double precoVenda = Double.parseDouble(precoVendaText);

            pedidoController.adicionarItem(pedidoId, produtoId, quantidade, precoVenda);
            System.out.println("Item adicionado com sucesso.");
        } catch (NumberFormatException ex) {
            System.err.println("Erro de formato: Certifique-se de digitar números válidos. Detalhe: " + ex.getMessage());
        }
    }

    // Método auxiliar para Remover Item (isolando a lógica de leitura segura)
    private void removerItemDoPedido(int pedidoId) {
        System.out.print("Digite o ID do produto a ser removido: ");
        String produtoIdText = scanner.nextLine().trim();

        try {
            int produtoIdRemover = Integer.parseInt(produtoIdText);
            pedidoController.removerItem(pedidoId, produtoIdRemover);
            System.out.println("Item removido com sucesso.");
        } catch (NumberFormatException ex) {
            System.err.println("Erro de formato: O ID do produto deve ser um número inteiro. Detalhe: " + ex.getMessage());
        }
    }

    // Método auxiliar para Alterar Quantidade (isolando a lógica de leitura segura)
    private void alterarQuantidadeItemDoPedido(int pedidoId) {
        System.out.print("Digite o ID do produto a ser alterado: ");
        String produtoIdText = scanner.nextLine().trim();
        System.out.print("Digite a nova quantidade: ");
        String novaQuantidadeText = scanner.nextLine().trim();

        try {
            int produtoIdAlterar = Integer.parseInt(produtoIdText);
            int novaQuantidade = Integer.parseInt(novaQuantidadeText);

            pedidoController.alterarQuantidadeItem(pedidoId, produtoIdAlterar, novaQuantidade);
            System.out.println("Quantidade alterada com sucesso.");
        } catch (NumberFormatException ex) {
            System.err.println("Erro de formato: Certifique-se de digitar números inteiros. Detalhe: " + ex.getMessage());
        }
    }

    private void listarPedidos() {
        System.out.println("\n--- Lista de Pedidos ---");
        try {
            List<Pedido> pedidos = pedidoController.listarPedidos();
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido cadastrado.");
            } else {
                for (Pedido pedido : pedidos) {
                    System.out.println(pedido);
                    // Supondo que getValorTotal() exista e não cause exceção
                    // System.out.println("Valor Total: " + pedido.getValorTotal());
                    System.out.println("--------------------");
                }
            }
        } catch (Exception ex) {
            System.err.println("Falha ao listar os pedidos. Detalhe: " + ex.getMessage());
        }
    }
}