package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.ClienteController;
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

    public MainView() {
        this.scanner = new Scanner(System.in);

        // --- INICIALIZAÇÃO E INJEÇÃO DE DEPENDÊNCIAS ---
        //
        // Seus "cérebros" (Controllers) agora precisam de "ferramentas" (Services e Repositories) para funcionar.
        // O MainView é o responsável por montar o 'kit de ferramentas' e entregar a cada Controller.
        // Antes, você estava chamando apenas new PedidoController(), que não sabe como montar o kit.
        // Agora, montamos o kit completo e passamos para ele.

        // 1. Criação das Repositórios (as 'ferramentas' de acesso a dados)
        ClienteRepositoryImpl clienteRepo = new ClienteRepositoryImpl();
        ProdutoRepositoryImpl produtoRepo = new ProdutoRepositoryImpl();
        PedidoRepositoryImpl pedidoRepo = new PedidoRepositoryImpl();

        // 2. Criação dos Serviços Auxiliares (como notificação)
        EmailNotificationServiceImpl notificationService = new EmailNotificationServiceImpl();

        // 2.1 Crie o Repositório de Cupom
        CupomRepository cupomRepo = new CupomRepositoryImpl();

        // 3. Criação dos Services (a lógica de negócio), injetando os Repositórios
        //  ClienteServiceImpl e ProdutoServiceImpl têm construtores de injeção!
        ClienteService clienteService = new ClienteServiceImpl(clienteRepo);
        ProdutoService produtoService = new ProdutoServiceImpl(produtoRepo);
        CupomService cupomService = new CupomServiceImpl(cupomRepo); // NOVO ARGUMENTO

        // 3. Crie o PedidoService, injetando todas as 5 dependências
        PedidoService pedidoService = new PedidoServiceImpl(
                pedidoRepo,         // 1. PedidoRepository
                clienteRepo,        // 2. ClienteRepository
                produtoRepo,        // 3. ProdutoRepository
                notificationService, // 4. NotificationService
                cupomService         // 5. CupomService (A correção!)
        );
        // 4. Criação dos Controllers, injetando os Services
        // CORREÇÃO DO ERRO: Passando os services necessários para cada controller.
        ClienteController clienteController = new ClienteController();
        ProdutoController produtoController = new ProdutoController();
        PedidoController pedidoController = new PedidoController(pedidoService);

        // 5. Criação das Views, injetando os Controllers e o Scanner
        this.clienteView = new ClienteView(clienteController, scanner);
        this.produtoView = new ProdutoView(produtoController, scanner);
        this.pedidoView = new PedidoView(pedidoController, scanner);
    }

    public void iniciar() {
        // ... (o método iniciar() permanece o mesmo)
        int opcao;
        do {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Gerenciar Clientes");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Gerenciar Pedidos");
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