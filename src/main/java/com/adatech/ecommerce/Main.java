package com.adatech.ecommerce;

import com.adatech.ecommerce.view.MainView;

/**
 * Ponto de entrada principal da aplicação de E-Commerce.
 */
public class Main {
    public static void main(String[] args) {
        // Cria uma instância da View principal e inicia a aplicação
        MainView mainView = new MainView();
        mainView.iniciar();
    }
}

