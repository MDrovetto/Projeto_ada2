package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.PedidoController;
import java.util.Scanner;

/**
 * View para interação com o usuário para o gerenciamento de Pedidos.
 * TODO:
 *  - No construtor, receber o PedidoController.
 *  - Criar um método para exibir o menu principal de pedidos (Criar Pedido, Gerenciar Pedido Existente, Listar Pedidos, Voltar).
 *  - Criar um método para gerenciar um pedido existente, com opções como:
 *    - Adicionar Item, Remover Item, Alterar Quantidade, Finalizar Pedido, Pagar, Marcar como Entregue.
 *  - Os métodos devem coletar os dados necessários do usuário e chamar o PedidoController.
 *  - Exibir os resultados e o status atualizado do pedido para o usuário.
 */
public class PedidoView {

    private final PedidoController pedidoController;
    private final Scanner scanner;

    public PedidoView(PedidoController pedidoController, Scanner scanner) {
        this.pedidoController = pedidoController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        // TODO: Implementar o loop do menu de pedidos
    }

    private void criarPedido() {
        // TODO: Implementar a lógica para coletar CPF do cliente e chamar o controller
    }

    private void gerenciarPedido() {
        // TODO: Implementar a lógica para selecionar um pedido e exibir o submenu de gerenciamento
    }

    private void listarPedidos() {
        // TODO: Implementar a lógica para chamar o controller e exibir a lista de pedidos
    }
}

