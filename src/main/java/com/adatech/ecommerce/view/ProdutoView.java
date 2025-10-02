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
        int opcao = -1;
        do {
            System.out.println("\n--- Menu de Produtos ---");
            System.out.println("1. Cadastrar Produto");
            System.out.println("2. Atualizar Produto");
            System.out.println("3. Listar Produtos");
            System.out.println("4. Buscar Produto por ID");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            // Refatoração com nextLine() + parse
            String entrada = scanner.nextLine().trim();

            try {
                opcao = Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                opcao = -1; // Garante que caia no default
                System.err.println("Entrada inválida. Por favor, digite apenas o número da opção. Detalhe: " + ex.getMessage());
                continue; // Volta para o início do loop
            }

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

        // Refatoração do Preço (BigDecimal)
        System.out.print("Digite o preço (Ex: 123.45): ");
        BigDecimal preco = null;
        try {
            String precoText = scanner.nextLine().trim().replace(",", "."); // Troca vírgula por ponto
            preco = new BigDecimal(precoText);
        } catch (NumberFormatException ex) {
            System.err.println("Erro: Preço inválido. O preço deve ser um número. Detalhe: " + ex.getMessage());
            return;
        }

        // Refatoração do Estoque (int)
        System.out.print("Digite o estoque inicial: ");
        int estoque = 0;
        try {
            estoque = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.err.println("Erro: Estoque inválido. O estoque deve ser um número inteiro. Detalhe: " + ex.getMessage());
            return;
        }

        Produto novoProduto = new Produto(0, nome, descricao, preco, estoque);
        produtoController.cadastrarProduto(novoProduto);
        System.out.println("Produto cadastrado com sucesso!");
    }

    private void atualizarProduto() {
        System.out.println("\n--- Atualização de Produto ---");
        System.out.print("Digite o ID do produto que deseja atualizar: ");

        // Refatoração do ID (int)
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.err.println("Erro: ID inválido. O ID deve ser um número inteiro. Detalhe: " + ex.getMessage());
            return;
        }

        Produto produtoExistente = produtoController.buscarProdutoPorId(id);
        if (produtoExistente == null) {
            System.err.println("Erro: Produto não encontrado com o ID " + id + ".");
            return;
        }

        System.out.println("Produto atual: " + produtoExistente.getNome());
        System.out.print("Digite o novo nome (deixe vazio para manter): ");
        String novoNome = scanner.nextLine();
        if (novoNome.isBlank()) {
            novoNome = produtoExistente.getNome();
        }

        System.out.println("Descrição atual: " + produtoExistente.getDescricao());
        System.out.print("Digite a nova descrição (deixe vazio para manter): ");
        String novaDescricao = scanner.nextLine();
        if (novaDescricao.isBlank()) {
            novaDescricao = produtoExistente.getDescricao();
        }

        // Refatoração do Estoque (int)
        System.out.println("Estoque atual: " + produtoExistente.getEstoque());
        System.out.print("Digite a nova quantidade em estoque (deixe vazio para manter): ");
        String estoqueText = scanner.nextLine().trim();
        int novoEstoque = produtoExistente.getEstoque();

        if (!estoqueText.isBlank()) {
            try {
                novoEstoque = Integer.parseInt(estoqueText);
            } catch (NumberFormatException ex) {
                System.err.println("Erro: Estoque inválido. Mantendo estoque anterior. Detalhe: " + ex.getMessage());
                // Permite continuar com o estoque antigo
            }
        }

        Produto produtoAtualizado = new Produto(id, novoNome, novaDescricao, produtoExistente.getPreco(), novoEstoque);
        produtoController.atualizarProduto(produtoAtualizado);
        System.out.println("Produto atualizado com sucesso!");
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

        // Refatoração do ID (int)
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.err.println("Erro: ID inválido. O ID deve ser um número inteiro. Detalhe: " + ex.getMessage());
            return;
        }

        Produto produto = produtoController.buscarProdutoPorId(id);
        if (produto != null) {
            System.out.println("Produto encontrado:");
            System.out.println(produto);
        } else {
            System.err.println("Produto não encontrado.");
        }
    }
}