package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.ProdutoController;
import java.util.Scanner;

/**
 * View para interação com o usuário para o gerenciamento de Produtos.
 * TODO:
 *  - No construtor, receber o ProdutoController.
 *  - Criar um método para exibir o menu de produtos (Cadastrar, Atualizar, Listar, Buscar por ID, Voltar).
 *  - Criar métodos para cada uma das opções do menu, que irão:
 *    - Solicitar os dados necessários ao usuário (nome, descrição, preço, etc.).
 *    - Chamar o método correspondente no ProdutoController.
 *    - Exibir o resultado da operação para o usuário (sucesso, erro, dados do produto, etc.).
 */
public class ProdutoView {

    private final ProdutoController produtoController;
    private final Scanner scanner;

    public ProdutoView(ProdutoController produtoController, Scanner scanner) {
        this.produtoController = produtoController;
        this.scanner = scanner;
    }

    public void exibirMenu() {
        // TODO: Implementar o loop do menu de produtos
    }

    private void cadastrarProduto() {
        // TODO: Implementar a lógica para coletar dados e chamar o controller
    }

    private void atualizarProduto() {
        // TODO: Implementar a lógica para coletar dados e chamar o controller
    }

    private void listarProdutos() {
        // TODO: Implementar a lógica para chamar o controller e exibir a lista
    }

    private void buscarProdutoPorId() {
        // TODO: Implementar a lógica para coletar ID e chamar o controller
    }
}

