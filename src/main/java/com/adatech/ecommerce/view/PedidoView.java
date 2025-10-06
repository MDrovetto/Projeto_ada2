package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.PedidoController;
import com.adatech.ecommerce.controller.ProdutoController;
import com.adatech.ecommerce.model.ItemVenda;
import com.adatech.ecommerce.model.Pedido;
import com.adatech.ecommerce.model.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class PedidoView {

    private final PedidoController pedidoController;
    private final ProdutoController produtoController;
    private final Scanner scanner;

    public PedidoView(PedidoController pedidoController, ProdutoController produtoController, Scanner scanner) {
        this.pedidoController = pedidoController;
        this.produtoController = produtoController;
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
            String entrada = scanner.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                opcao = -1;
            }
            switch (opcao) {
                case 1: criarPedido(); break;
                case 2: gerenciarPedido(); break;
                case 3: listarPedidos(); break;
                case 0: System.out.println("Voltando ao menu principal..."); break;
                default: System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void criarPedido() {
        System.out.println("\n--- Criar Pedido ---");
        System.out.print("Digite o CPF do cliente para o novo pedido ou 0 para cancelar: ");
        String cpfCliente = scanner.nextLine().trim();

        if (cpfCliente.equals("0")) {
            return;
        }

        try {
            Pedido novoPedido = pedidoController.criarPedido(cpfCliente);
            if (novoPedido != null) {
                System.out.println("Pedido criado com sucesso! ID do pedido: " + novoPedido.getId());

            while (true) {
                System.out.print("Deseja adicionar itens a este pedido agora? Escolha uma opção (1) Sim ou (2) Não: ");
                String entrada = scanner.nextLine().trim();
            int opcao;

            try {
                opcao = Integer.parseInt(entrada);
            }
            catch (NumberFormatException e) {
                        // Se o usuário digitar um texto, a opção se torna inválida
                        opcao = -1;
                    }
            switch (opcao)
            {
                case 1:
                    gerenciarPedido(novoPedido.getId());
                    return;
                case 2:
                    return;

                default:
                    System.err.println("Opção inválida. Por favor, digite 1 para Sim ou 2 para Não.");
                    }
                }
            }
        }
        catch (Exception ex) {
            System.err.println("Erro ao criar pedido: " + ex.getMessage());
        }
    }

    private void gerenciarPedido() {
        System.out.print("\nDigite o ID do pedido que deseja gerenciar: ");
        int idPedido;
        try {
            idPedido = Integer.parseInt(scanner.nextLine().trim());
            gerenciarPedido(idPedido);
        } catch (NumberFormatException ex) {
            System.err.println("Erro: ID de pedido inválido. Deve ser um número.");
        }
    }

    private void gerenciarPedido(int idPedido) {
        int opcao;
        do {
            Pedido pedido = pedidoController.buscarPedidoPorId(idPedido);
            if (pedido == null) {
                System.err.println("Atenção: Pedido ID " + idPedido + " não encontrado.");
                return;
            }



            exibirResumoDoPedido(pedido);
            System.out.println("1. Adicionar Item");
            System.out.println("2. Remover Item");
            System.out.println("3. Alterar Quantidade do Item");
            System.out.println("4. Finalizar Pedido");
            System.out.println("5. Pagar Pedido");
            System.out.println("6. Entregar Pedido");
            System.out.println("0. Voltar ao menu de pedidos");
            System.out.print("Escolha uma opção: ");

            String entrada = scanner.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                opcao = -1;
            }

            try {
                switch (opcao) {
                    case 1:
                        adicionarItemAoPedido(pedido);
                        break;
                    case 2:
                        removerItemDoPedido(pedido);
                        break;
                    case 3:
                        alterarQuantidadeItemDoPedido(pedido);
                        break;
                    case 4:
                        pedidoController.finalizarPedido(idPedido);
                        break;
                    case 5:
                        pedidoController.pagarPedido(idPedido);
                        break;
                    case 6:
                        pedidoController.entregarPedido(idPedido);
                        break;
                    case 0:
                        System.out.println("Voltando ao menu principal");
                        break;
                    default:
                        System.err.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception ex) {
                System.err.println("\nERRO: " + ex.getMessage());
            }

        } while (opcao != 0);
    }

    private void exibirResumoDoPedido(Pedido pedido) {
        System.out.println("\n+-----------------------------------------------------+");
        System.out.println("| RESUMO DO PEDIDO ID: " + pedido.getId() + " | STATUS: " + pedido.getStatus());
        System.out.println("+-----------------------------------------------------+");
        if (pedido.getItens().isEmpty()) {
            System.out.println("| Carrinho vazio.                                     |");
        } else {
            System.out.printf("| %-30s | %-5s | %-10s |\n", "Produto", "Qtd", "Subtotal");
            System.out.println("|--------------------------------+-------+------------|");
            for (ItemVenda item : pedido.getItens()) {
                System.out.printf("| %-30s | %-5d | R$ %-8.2f |\n",
                        item.getProduto().getNome(),
                        item.getQuantidade(),
                        item.calcularSubtotal());
            }
        }
        System.out.println("+--------------------------------+-------+------------+");
        System.out.printf("| Total Bruto: R$ %-35.2f |\n", pedido.getValorBruto());
        System.out.println("+-----------------------------------------------------+\n");
    }

    private void adicionarItemAoPedido(Pedido pedido) {
        System.out.println("\n--- Adicionar Item ao Pedido ---");
        System.out.println("Produtos disponíveis:");

        List<Produto> produtos = produtoController.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado no sistema.");
            return;
        }

        System.out.printf("%-5s | %-30s | %-10s\n", "ID", "Nome", "Preço");
        System.out.println("------+--------------------------------+-----------");
        for (Produto produto : produtos) {
            System.out.printf("%-5d | %-30s | R$ %-8.2f\n", produto.getId(), produto.getNome(), produto.getPreco());
        }

        try {
            System.out.print("\nDigite o ID do produto que deseja adicionar: ");
            int produtoId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Digite a quantidade: ");
            int quantidade = Integer.parseInt(scanner.nextLine().trim());

            // Sugere o preço padrão, mas permite alteração
            Produto produtoSelecionado = produtoController.buscarProdutoPorId(produtoId);
            if (produtoSelecionado == null) {
                System.err.println("Produto com ID " + produtoId + " não encontrado.");
                return;
            }

            System.out.print("Digite o preço de venda (padrão: " + produtoSelecionado.getPreco() + "): ");
            String precoVendaText = scanner.nextLine().trim().replace(",", ".");

            BigDecimal precoVenda;
            if (precoVendaText.isEmpty()) {
                precoVenda = produtoSelecionado.getPreco();
            } else {
                precoVenda = new BigDecimal(precoVendaText);
            }

            // ATENÇÃO: AQUI USAMOS BIGDECIMAL, NÃO DOUBLE
            pedidoController.adicionarItem(pedido.getId(), produtoId, quantidade, precoVenda);
            System.out.println("Item adicionado com sucesso.");

        } catch (NumberFormatException ex) {
            System.err.println("Erro de formato: Certifique-se de digitar números válidos.");
        }
    }

    // Os métodos de remover e alterar também recebem o objeto Pedido para maior eficiência
    private void removerItemDoPedido(Pedido pedido) {
        if (pedido.getItens().isEmpty()) {
            System.err.println("O carrinho já está vazio.");
            return;
        }
        System.out.print("Digite o ID do produto a ser removido do carrinho: ");
        try {
            int produtoIdRemover = Integer.parseInt(scanner.nextLine().trim());
            pedidoController.removerItem(pedido.getId(), produtoIdRemover);
            System.out.println("Item removido com sucesso.");
        } catch (NumberFormatException ex) {
            System.err.println("Erro de formato: O ID do produto deve ser um número inteiro.");
        }
    }

    private void alterarQuantidadeItemDoPedido(Pedido pedido) {
        if (pedido.getItens().isEmpty()) {
            System.err.println("O carrinho está vazio.");
            return;
        }
        try {
            System.out.print("Digite o ID do produto a ser alterado: ");
            int produtoIdAlterar = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Digite a nova quantidade: ");
            int novaQuantidade = Integer.parseInt(scanner.nextLine().trim());
            pedidoController.alterarQuantidadeItem(pedido.getId(), produtoIdAlterar, novaQuantidade);
            System.out.println("Quantidade alterada com sucesso.");
        } catch (NumberFormatException ex) {
            System.err.println("Erro de formato: Certifique-se de digitar números inteiros.");
        }
    }

    private void listarPedidos() {
        // (Este método permanece igual ao seu, mas pode ser aprimorado para mostrar mais detalhes)
        System.out.println("\n--- Lista de Todos os Pedidos ---");
        try {
            List<Pedido> pedidos = pedidoController.listarPedidos();
            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido cadastrado.");
            } else {
                for (Pedido pedido : pedidos) {
                    System.out.println("ID: " + pedido.getId() + " | Cliente: " + pedido.getCliente().getNome() +
                            " | Status: " + pedido.getStatus() + " | Valor Total: R$ " + pedido.getValorTotal());
                    System.out.println("--------------------------------------------------");
                }
            }
        } catch (Exception ex) {
            System.err.println("Falha ao listar os pedidos: " + ex.getMessage());
        }
    }
}