package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.ClienteController;
import com.adatech.ecommerce.controller.PedidoController;
import com.adatech.ecommerce.controller.ProdutoController;
import java.util.Scanner;

public class MainView {

     private final Scanner scanner;
     private final ClienteView clienteView; // A ser criada
     private final ProdutoView produtoView; // A ser criada
     private final PedidoView pedidoView;   // A ser criada

    public MainView() {
        this.scanner = new Scanner(System.in);
        // Instancia os controladores, que por sua vez instanciam os serviços e repositórios.
        ClienteController clienteController = new ClienteController();
        ProdutoController produtoController = new ProdutoController();
        PedidoController pedidoController = new PedidoController();

        // Passa os controladores e o scanner para as views.
        this.clienteView = new ClienteView(clienteController, scanner);
        this.produtoView = new ProdutoView(produtoController, scanner);
        this.pedidoView = new PedidoView(pedidoController, scanner);
    }

    public void iniciar() {
        // TODO: Implementar o loop do menu principal
        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Pedidos");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

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
                case 0:
                    System.out.println("Encerrando a aplicação...");
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);

        scanner.close(); // Fecha o scanner ao sair do programa.

    }
}

