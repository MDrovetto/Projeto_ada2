package com.adatech.ecommerce.model;

import java.io.Serializable;

public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private String cpf;
    private String email;
    private String endereco;

    public Cliente(int id, String nome, String cpf, String email, String endereco) {
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

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve ter 11 dígitos.");
        }
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Formato de e-mail inválido.");
        }
        this.email = email.trim();
    }

    public String getEndereco() {
        return endereco;
    }

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