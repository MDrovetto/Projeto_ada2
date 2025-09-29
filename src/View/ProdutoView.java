package View;

import Controller.ProdutoController;
import model.Produto;
import java.math.BigDecimal;
import java.util.Scanner;

public class ProdutoView {
    private Scanner scanner;
    private ProdutoController controller;

    public ProdutoView(ProdutoController controller) {
        this.scanner = new Scanner(System.in);
        this.controller = controller;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n=== MENU PRODUTOS ===");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Listar Produtos");
            System.out.println("3. Buscar Produto por ID");
            System.out.println("4. Atualizar Produto");
            System.out.println("5. Excluir Produto");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine()); // ✅ nextLine() + parse

                switch (opcao) {
                    case 1:
                        cadastrarProduto();
                        break;
                    case 2:
                        listarProdutos();
                        break;
                    case 3:
                        buscarProdutoPorId();
                        break;
                    case 4:
                        atualizarProduto();
                        break;
                    case 5:
                        excluirProduto();
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

    private void cadastrarProduto() {
        try {
            System.out.println("\n--- CADASTRAR PRODUTO ---");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Preço: ");
            BigDecimal preco = new BigDecimal(scanner.nextLine()); // ✅ nextLine() + parse

            System.out.print("Quantidade em Estoque: ");
            Integer quantidadeEstoque = Integer.parseInt(scanner.nextLine()); // ✅ nextLine() + parse

            controller.cadastrarProduto(nome, descricao, preco, quantidadeEstoque);
            System.out.println("Produto cadastrado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: Preço e quantidade devem ser números válidos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage()); // ✅ e.getMessage()
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void listarProdutos() {
        System.out.println("\n--- LISTA DE PRODUTOS ---");
        controller.listarProdutos().forEach(System.out::println);
    }

    private void buscarProdutoPorId() {
        try {
            System.out.print("\nDigite o ID do produto: ");
            Long id = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            Produto produto = controller.buscarProdutoPorId(id);
            if (produto != null) {
                System.out.println("Produto encontrado: " + produto);
            } else {
                System.out.println("Produto não encontrado!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao buscar produto: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void atualizarProduto() {
        try {
            System.out.print("\nDigite o ID do produto a ser atualizado: ");
            Long id = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            System.out.print("Novo nome: ");
            String nome = scanner.nextLine();

            System.out.print("Nova descrição: ");
            String descricao = scanner.nextLine();

            System.out.print("Novo preço: ");
            BigDecimal preco = new BigDecimal(scanner.nextLine()); // ✅ nextLine() + parse

            System.out.print("Nova quantidade em estoque: ");
            Integer quantidadeEstoque = Integer.parseInt(scanner.nextLine()); // ✅ nextLine() + parse

            controller.atualizarProduto(id, nome, descricao, preco, quantidadeEstoque);
            System.out.println("Produto atualizado com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: Preço, quantidade e ID devem ser números válidos.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage()); // ✅ e.getMessage()
        } catch (Exception e) {
            System.out.println("Erro ao atualizar produto: " + e.getMessage()); // ✅ e.getMessage()
        }
    }

    private void excluirProduto() {
        try {
            System.out.print("\nDigite o ID do produto a ser excluído: ");
            Long id = Long.parseLong(scanner.nextLine()); // ✅ nextLine() + parse

            controller.excluirProduto(id);
            System.out.println("Produto excluído com sucesso!");

        } catch (NumberFormatException e) {
            System.out.println("Erro: ID deve ser um número válido.");
        } catch (Exception e) {
            System.out.println("Erro ao excluir produto: " + e.getMessage()); // ✅ e.getMessage()
        }
    }
}
