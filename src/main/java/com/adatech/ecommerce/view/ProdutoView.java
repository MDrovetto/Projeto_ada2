package com.adatech.ecommerce.view;


import com.adatech.ecommerce.controller.ProdutoController;
import com.adatech.ecommerce.model.Produto;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class ProdutoView {

    private final ProdutoController produtoController;
    private final Scanner scanner;

    public ProdutoView(ProdutoController produtoController, Scanner scanner) {
        this.produtoController = produtoController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n--- Menu de Produtos ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Atualizar Produto");
            System.out.println("3. Listar Produtos");
            System.out.println("4. Buscar Produto por ID");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Consome a quebra de linha

            switch (opcao) {
                case 1:
                    cadastrarProduto();
                    break;
                case 2:
                    atualizarProduto();
                    break;
                case 3:
                    listarProdutos();
                    break;
                case 4:
                    buscarProdutoPorId();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.err.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    private void cadastrarProduto() {
        System.out.println("\n--- Cadastro de Produto ---");
        System.out.print("Digite o nome do produto: ");
        String nome = scanner.nextLine();
        System.out.print("Digite a descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Digite o preço: ");
        BigDecimal preco = scanner.nextBigDecimal();
        scanner.nextLine();
        System.out.print("Digite o estoque inicial: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();

        Produto novoProduto = new Produto(0, nome, descricao, preco, estoque);
        produtoController.cadastrarProduto(novoProduto);
    }

    private void atualizarProduto() {
        System.out.println("\n--- Atualização de Produto ---");
        System.out.print("Digite o ID do produto que deseja atualizar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produtoExistente = produtoController.buscarProdutoPorId(id);
        if (produtoExistente == null) {
            System.err.println("Erro: Produto não encontrado.");
            return;
        }

        System.out.print("Digite o novo nome (ou 'n' para manter o atual): ");
        String novoNome = scanner.nextLine();
        if (novoNome.equalsIgnoreCase("n")) {
            novoNome = produtoExistente.getNome();
        }

        System.out.print("Digite a nova descrição (ou 'n' para manter a atual): ");
        String novaDescricao = scanner.nextLine();
        if (novaDescricao.equalsIgnoreCase("n")) {
            novaDescricao = produtoExistente.getDescricao();
        }

        System.out.print("Digite a nova quantidade em estoque (ou -1 para manter a atual): ");
        int novoEstoque = scanner.nextInt();
        scanner.nextLine();
        if (novoEstoque == -1) {
            novoEstoque = produtoExistente.getEstoque();
        }

        Produto produtoAtualizado = new Produto(id, novoNome, novaDescricao, produtoExistente.getPreco(), novoEstoque);
        produtoController.atualizarProduto(produtoAtualizado);

    }

    private void listarProdutos() {
        System.out.println("\n--- Lista de Produtos ---");
        List<Produto> produtos = produtoController.listarProdutos();
        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        } else {
            for (Produto produto : produtos) {
                System.out.println(produto);
            }
        }
    }

    private void buscarProdutoPorId() {
        System.out.println("\n--- Busca de Produto por ID ---");
        System.out.print("Digite o ID do produto: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = produtoController.buscarProdutoPorId(id);
        if (produto != null) {
            System.out.println("Produto encontrado:");
            System.out.println(produto);
        } else {
            System.err.println("Produto não encontrado.");
        }

    }
}

