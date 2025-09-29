package View;

import Controller.PedidoController;
import model.Pedido;
import java.math.BigDecimal;
import java.util.Scanner;

public class PedidoView {
    private Scanner scanner;
    private PedidoController controller;

    public PedidoView(PedidoController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== MENU PEDIDOS ===");
            System.out.println("1. Criar Pedido");
            System.out.println("2. Listar Pedidos");
            System.out.println("3. Buscar Pedido por ID");
            System.out.println("4. Adicionar Item ao Pedido");
            System.out.println("5. Remover Item do Pedido");
            System.out.println("6. Calcular Total do Pedido");
            System.out.println("7. Finalizar Pedido");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine()); // ✅ nextLine() + parse

                switch (opcao) {
                    case 1:
                        criarPedido();
                        break;
                    case 2:
                        listarPedidos();
                        break;
                    case 3:
                        buscarPedidoPorId();
                        break;
                    case 4:
                        adicionarItemPedido();
                        break;
                    case 5:
                        removerItemPedido();
                        break;
                    case 6:
                        calcularTotalPedido();
                        break;
                    case 7:
                        finalizarPedido();
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

    private void criarPedido() {
        try {
            System.out.println("\n--- CRIAR PEDIDO ---");
            System.out.print("ID do Cliente: ");
            Long clienteId = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            controller.criarPedido(clienteId);
            System.out.println("Pedido criado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID do cliente deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao criar pedido: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void listarPedidos() {
        System.out.println("\n--- LISTA DE PEDIDOS ---");
        controller.listarPedidos().forEach(System.out::println);
    }

    private void buscarPedidoPorId() {
        try {
            System.out.print("\nDigite o ID do pedido: ");
            Long id = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            Pedido pedido = controller.buscarPedidoPorId(id);
            if (pedido != null) {
                System.out.println("Pedido encontrado: " + pedido);
            } else {
                System.out.println("Pedido não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar pedido: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void adicionarItemPedido() {
        try {
            System.out.print("\nID do Pedido: ");
            Long pedidoId = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            System.out.print("ID do Produto: ");
            Long produtoId = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            System.out.print("Quantidade: ");
            Integer quantidade = Integer.parseInt(scanner.nextLine()); // ✅ nextLine() + parse

            controller.adicionarItemPedido(pedidoId, produtoId, quantidade);
            System.out.println("Item adicionado ao pedido com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: IDs e quantidade devem ser números válidos.");
        } catch (Exception e) {
            System.out.println("Erro ao adicionar item: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void removerItemPedido() {
        try {
            System.out.print("\nID do Pedido: ");
            Long pedidoId = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            System.out.print("ID do Produto: ");
            Long produtoId = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            controller.removerItemPedido(pedidoId, produtoId);
            System.out.println("Item removido do pedido com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: IDs devem ser números válidos.");
        } catch (Exception e) {
            System.out.println("Erro ao remover item: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void calcularTotalPedido() {
        try {
            System.out.print("\nID do Pedido: ");
            Long pedidoId = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            BigDecimal total = controller.calcularTotalPedido(pedidoId);
            System.out.println("Total do pedido: R$ " + total);

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao calcular total: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void finalizarPedido() {
        try {
            System.out.print("\nID do Pedido: ");
            Long pedidoId = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            controller.finalizarPedido(pedidoId);
            System.out.println("Pedido finalizado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao finalizar pedido: " + e.getMessage()); // ✅ e.getMessage()
        }
    }
}