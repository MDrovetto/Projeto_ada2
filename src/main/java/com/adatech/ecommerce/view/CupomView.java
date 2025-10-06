package com.adatech.ecommerce.view;

import com.adatech.ecommerce.controller.CupomController;
import com.adatech.ecommerce.model.Cupom;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CupomView {

    private final CupomController cupomController;
    private final Scanner scanner;
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CupomView(CupomController cupomController, Scanner scanner) {
        this.cupomController = cupomController;
        this.scanner = scanner;
    }

    public void exibirMenu() {

        while (true) {
            System.out.println("\n------Menu Cupons------");
            System.out.println("(1) Cadastrar Novo Cupom");
            System.out.println("(2) Listar Cupons");
            System.out.println("(3) Simular Desconto");
            System.out.println("(0) Voltar");
            System.out.print("Escolha uma opção: ");

            String opcao = scanner.nextLine().trim();
            switch (opcao) {
                case "1":
                    cadastrarCupom();
                    break;
                case "2":
                    listarCupons();
                    break;
                case "3":
                    simularDesconto();
                    break;
                case "0":
                    return;
                default:
                    System.err.println("\nOpção inválida, tente novamente.");
            }
        }
    }

    private void cadastrarCupom() {
        System.out.println("\n--- Cadastro de Cupom ---");
        System.out.print("Código do Cupom: ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        BigDecimal valorFixo = BigDecimal.ZERO;
        BigDecimal percentual = BigDecimal.ZERO;

        System.out.print("Tipo de Desconto (1: Fixo | 2: Percentual): ");
        String tipo = scanner.nextLine().trim();

        try {
            System.out.print("Valor do Desconto (Use ponto como separador decimal): ");
            String valorText = scanner.nextLine().trim().replace(",", ".");
            BigDecimal valor = new BigDecimal(valorText);

            if (tipo.equals("1")) {
                valorFixo = valor;
            } else if (tipo.equals("2")) {
                // Para percentual, se o usuário digitar 10, converte para 0.10
                percentual = valor.divide(new BigDecimal("100"));
            } else {
                System.err.println("Tipo de desconto inválido. Cupom não será criado.");
                return;
            }
        } catch (NumberFormatException ex) {
            System.err.println("Erro: Valor do desconto inválido. Use apenas números. Detalhe: " + ex.getMessage());
            return;
        }

        LocalDate validade = null;
        System.out.print("Data de Validade (formato dd/MM/yyyy): ");
        String validadeText = scanner.nextLine().trim();
        try {
            validade = LocalDate.parse(validadeText, DATE_FORMAT);
        } catch (DateTimeParseException ex) {
            System.err.println("Erro: Formato de data inválido. Use dd/MM/yyyy. Detalhe: " + ex.getMessage());
            return;
        }

        BigDecimal valorMinimo = BigDecimal.ZERO;
        System.out.print("Valor Mínimo do Pedido (R$): ");
        try {
            String minimoText = scanner.nextLine().trim().replace(",", ".");
            valorMinimo = new BigDecimal(minimoText);
        } catch (NumberFormatException ex) {
            System.err.println("Erro: Valor mínimo inválido. Usando R$0.00. Detalhe: " + ex.getMessage());
        }

        Cupom novoCupom = new Cupom(0, codigo, valorFixo, percentual, validade, valorMinimo);
        Cupom cadastrado = cupomController.cadastrarCupom(novoCupom);
        if (cadastrado != null) {
            System.out.println("Cupom cadastrado: " + cadastrado);
        }
    }

    private void listarCupons() {
        System.out.println("\n--- Lista de Cupons ---");
        List<Cupom> cupons = cupomController.listarCupons();
        if (cupons.isEmpty()) {
            System.out.println("Nenhum cupom cadastrado.");
        } else {
            for (Cupom cupom : cupons) {
                System.out.println(cupom);
            }
        }
    }

    private void simularDesconto() {
        System.out.println("\n--- Simulação de Desconto ---");
        System.out.print("Digite o código do cupom: ");
        String codigo = scanner.nextLine().trim().toUpperCase();

        BigDecimal valorPedido = BigDecimal.ZERO;
        System.out.print("Digite o valor do pedido (R$): ");

        try {
            String valorText = scanner.nextLine().trim().replace(",", ".");
            valorPedido = new BigDecimal(valorText);
        } catch (NumberFormatException ex) {
            System.err.println("Erro: Valor do pedido inválido. Detalhe: " + ex.getMessage());
            return;
        }

        System.out.println("Valor Original: R$" + valorPedido);

        BigDecimal novoValor = cupomController.tentarAplicarDesconto(codigo, valorPedido);

        if (novoValor.compareTo(valorPedido) != 0) {
            BigDecimal desconto = valorPedido.subtract(novoValor);
            System.out.printf("Desconto Aplicado: R$%.2f%n", desconto);
            System.out.printf("Novo Valor do Pedido: R$%.2f%n", novoValor);
        }
    }
}