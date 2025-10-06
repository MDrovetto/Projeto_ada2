package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.ClienteController;
import com.adatech.ecommerce.controller.CupomController;
import com.adatech.ecommerce.controller.PedidoController;
import com.adatech.ecommerce.controller.ProdutoController;
import com.adatech.ecommerce.repository.*; // Importando todas as implementações
import com.adatech.ecommerce.service.*; // Importando todas as implementações de serviço
import java.util.Scanner;

public class MainView {

    private final Scanner scanner;
    private final ClienteView clienteView;
    private final ProdutoView produtoView;
    private final PedidoView pedidoView;
    private final CupomView cupomView;

    public MainView() {
        this.scanner = new Scanner(System.in);

        ClienteRepository clienteRepository = new ClienteRepositoryImpl();
        ProdutoRepository produtoRepository = new ProdutoRepositoryImpl();
        PedidoRepository pedidoRepository = new PedidoRepositoryImpl();
        CupomRepository cupomRepository = new CupomRepositoryImpl();


        NotificationService notificationService = new EmailNotificationServiceImpl();
        ClienteService clienteService = new ClienteServiceImpl(clienteRepository);
        ProdutoService produtoService = new ProdutoServiceImpl(produtoRepository);
        CupomService cupomService = new CupomServiceImpl(cupomRepository);
        PedidoService pedidoService = new PedidoServiceImpl(
                pedidoRepository,
                clienteRepository,
                produtoRepository,
                notificationService,
                cupomService);

        ClienteController clienteController = new ClienteController(clienteService);
        ProdutoController produtoController = new ProdutoController(produtoService);
        PedidoController pedidoController = new PedidoController(pedidoService);
        CupomController cupomController = new CupomController(cupomService);

        this.clienteView = new ClienteView(clienteController, scanner);
        this.produtoView = new ProdutoView(produtoController, scanner);
        this.pedidoView = new PedidoView(pedidoController, scanner);
        this.cupomView = new CupomView(cupomController, scanner);
    }


    public void iniciar() {
        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Pedidos");
            System.out.println("4. Gerenciar Cupons de Desconto");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            // Refatoração de segurança na leitura
            String entrada = scanner.nextLine().trim();
            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                opcao = -1; // Garante que caia no default
            }

            switch (opcao) {
                case 1:
                    clienteView.exibirMenu();
                    break;
                case 2:
                    produtoView.exibirMenu();
                    break;
                case 3:
                    pedidoView.exibirMenu();
                    break;
                    case 4:
                    cupomView.exibirMenu();
                    break;
                case 0:
                    System.out.println("Encerrando a aplicação...");
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close();
    }
}