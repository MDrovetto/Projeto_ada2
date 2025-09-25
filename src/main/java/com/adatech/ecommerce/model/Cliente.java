package com.adatech.ecommerce.model;

public class Cliente {

        private int id;
        private String nome;
        private String cpf;
        private String email;
        private String endereco;

        public Cliente(int id, String nome, String cpf, String email, String endereco) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.email = email;
            this.endereco = endereco;
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
         this.nome = nome;
    }

    public String getCpf() {
         return cpf;
    }

    public void setCpf(String cpf) {
         this.cpf = cpf;
    }

        public String getEmail() {
         return email;
    }

    public void setEmail(String email) {
         this.email = email;
    }

    public String getEndereco() {
         return endereco;
    }

   public void setEndereco(String endereco) {
         this.endereco = endereco;
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

