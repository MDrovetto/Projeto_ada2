
import java.security.Provider;
import java.util.Scanner;
import Controller.ClienteController;
import Controller.PedidoController;
import Controller.ProdutoController;
import Repository.impl.impl.ClienteRepositoryImpl;
import Repository.impl.impl.PedidoRepositoryImpl;
import Repository.impl.impl.ProdutoRepositoryImpl;
import View.ClienteView;
import View.PedidoView;
import View.ProdutoView;
import Service.impl.ClienteServiceImpl;
import Service.impl.ProdutoServiceImpl;
import Repository.impl.ClienteRepository;
import Repository.impl.ProdutoRepository;
import Repository.impl.PedidoRepository;
import notification.ConsoleNotificationService;

    public class Main {
        public static void main(String[] args) {
            // Instanciando repositórios
            ClienteRepositoryImpl clienteRepository = new ClienteRepositoryImpl();
            ProdutoRepositoryImpl produtoRepository = new ProdutoRepositoryImpl();
            PedidoRepositoryImpl pedidoRepository = new PedidoRepositoryImpl();
            ConsoleNotificationService notificationService = new ConsoleNotificationService();

            // Instanciando serviços
            ClienteServiceImpl clienteService = new ClienteServiceImpl(clienteRepository);
            ProdutoServiceImpl produtoService = new ProdutoServiceImpl(produtoRepository);

            // Instanciando controllers
            ClienteController clienteController = new ClienteController(clienteService);
            ProdutoController produtoController = new ProdutoController(produtoService);

            // PedidoController usando a Opção 1 ✅
            PedidoController pedidoController = new PedidoController(
                    pedidoRepository,
                    clienteRepository,
                    produtoRepository,
                    notificationService
            );

            // Instanciando views
            ClienteView clienteView = new ClienteView(clienteController);
            ProdutoView produtoView = new ProdutoView(produtoController);
            PedidoView pedidoView = new PedidoView(pedidoController);

            // Menu principal
            Scanner scanner = new Scanner(System.in);
            int opcao;

            do {
                System.out.println("\n=== SISTEMA DE PEDIDOS ===");
                System.out.println("1. Gerenciar Clientes");
                System.out.println("2. Gerenciar Produtos");
                System.out.println("3. Gerenciar Pedidos");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine()); // ✅ nextLine() + parse

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
                            System.out.println("Saindo do sistema...");
                            break;
                        default:
                            System.out.println("Opção inválida!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Por favor, digite um número válido.");
                    opcao = -1;
                }
            } while (opcao != 0);

            scanner.close();
        }
    }