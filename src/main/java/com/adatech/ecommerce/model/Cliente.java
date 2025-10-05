package com.adatech.ecommerce.model;

import java.io.Serializable; // 1. Importação necessária

public class Cliente implements Serializable { // 2. Implementa a interface

    // A linha abaixo é opcional, mas recomendada para controlar a versão da classe
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String endereco;

    // Construtor principal
    public Cliente(int id, String nome, String cpf, String email, String endereco) {
        // Delegamos a validação para os setters
        this.id = id;
        setNome(nome);
        setCpf(cpf);
        setEmail(email);
        setEndereco(endereco);
    }

    public static Cliente novo(String nome, String cpf, String email, String endereco) {
        return new Cliente(0, nome, cpf, email, endereco);
    }

    public static Cliente existente(int id, String nome, String cpf, String email, String endereco) {
        return new Cliente(id, nome, cpf, email, endereco);
    }

    public int getId() {
        return id;
    }

    // Não precisamos de validação no setId, pois o ID é geralmente interno ao sistema
    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    // Tratamento de Exceção: O nome não pode ser nulo ou vazio
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getCpf() {
        return cpf;
    }

    // Tratamento de Exceção: O CPF não pode ser nulo ou ter comprimento inválido
    public void setCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            // Em um sistema real, faríamos a validação matemática do CPF aqui ou no Service
            throw new IllegalArgumentException("CPF inválido. Deve ter 11 dígitos.");
        }
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    // Tratamento de Exceção: Validação básica de email
    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
        this.email = email.trim();
    }

    public String getEndereco() {
        return endereco;
    }

    // Tratamento de Exceção: O endereço não pode ser nulo ou vazio
    public void setEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            throw new IllegalArgumentException("O endereço do cliente não pode ser vazio.");
        }
        this.endereco = endereco.trim();
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", email='" + email + '\'' +
                ", endereco='" + endereco + '\'' +
                '}';
    }
}