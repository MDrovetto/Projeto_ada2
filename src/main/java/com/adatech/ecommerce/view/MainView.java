package com.adatech.ecommerce.view;

import java.util.Scanner;

/**
 * View principal que exibe o menu principal da aplicação.
 * TODO:
 *  - Criar um método para exibir o menu principal (Gerenciar Clientes, Gerenciar Produtos, Gerenciar Pedidos, Sair).
 *  - Criar um método para iniciar a interação com o usuário, lendo a opção escolhida.
 *  - Com base na opção, instanciar e chamar a view correspondente (ClienteView, ProdutoView, PedidoView).
 *  - Manter o menu em um loop até que o usuário escolha a opção "Sair".
 */
public class MainView {

    private final Scanner scanner;
    // private final ClienteView clienteView; // A ser criada
    // private final ProdutoView produtoView; // A ser criada
    // private final PedidoView pedidoView;   // A ser criada

    public MainView() {
        this.scanner = new Scanner(System.in);
        // this.clienteView = new ClienteView();
        // this.produtoView = new ProdutoView();
        // this.pedidoView = new PedidoView();
    }

    public void iniciar() {
        // TODO: Implementar o loop do menu principal
    }
}

