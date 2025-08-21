# E-Commerce Ada Tech - Projeto de Estudo

Este projeto é um sistema de E-Commerce desenvolvido como um exercício para a Ada Tech. O objetivo é aplicar os conceitos de Programação Orientada a Objetos (POO) e os princípios SOLID.

## Estrutura do Projeto

O projeto está organizado em pacotes que separam as responsabilidades da aplicação:

- `com.adatech.ecommerce.model`: Contém as classes de domínio (entidades) do sistema.
- `com.adatech.ecommerce.repository`: Responsável pelo acesso e persistência dos dados (em memória).
- `com.adatech.ecommerce.service`: Contém a lógica de negócio da aplicação.
- `com.adatech.ecommerce.controller`: Faz a ponte entre a interface do usuário (View) e os serviços.
- `com.adatech.ecommerce.view`: Responsável pela interação com o usuário (interface de console).

---

## To-Do List do Projeto

### Model (`com.adatech.ecommerce.model`)
- **Cliente.java**:
  - [ ] Adicionar atributos: id, nome, cpf, email, endereco.
  - [ ] Implementar construtor, getters e setters.
  - [ ] Sobrescrever `toString()`.
- **Produto.java**:
  - [ ] Adicionar atributos: id, nome, descricao, preco, estoque.
  - [ ] Implementar construtor, getters e setters.
  - [ ] Sobrescrever `toString()`.
- **Pedido.java**:
  - [ ] Adicionar atributos: id, cliente, dataCriacao, itens, valorTotal, status.
  - [ ] Implementar construtor (status inicial = ABERTO).
  - [ ] Implementar métodos para adicionar/remover item e calcular total.
  - [ ] Sobrescrever `toString()`.
- **ItemVenda.java**:
  - [ ] Adicionar atributos: produto, quantidade, precoVenda.
  - [ ] Implementar construtor, getters e setters.
  - [ ] Implementar método para calcular subtotal.
  - [ ] Sobrescrever `toString()`.
- **StatusPedido.java**:
  - [ ] Definir os enums: `ABERTO`, `AGUARDANDO_PAGAMENTO`, `PAGO`, `FINALIZADO`.

### Repository (`com.adatech.ecommerce.repository`)
- **ClienteRepositoryImpl.java**:
  - [ ] Implementar `salvar`, `listarTodos`, `buscarPorId` e `buscarPorCpf` usando um `Map`.
- **ProdutoRepositoryImpl.java**:
  - [ ] Implementar `salvar`, `listarTodos` e `buscarPorId` usando um `Map`.
- **PedidoRepositoryImpl.java**:
  - [ ] Implementar `salvar`, `listarTodos` e `buscarPorId` usando um `Map`.

### Service (`com.adatech.ecommerce.service`)
- **ClienteServiceImpl.java**:
  - [ ] Implementar a lógica de negócio para cadastrar (com validação de CPF único) e atualizar clientes.
- **ProdutoServiceImpl.java**:
  - [ ] Implementar a lógica de negócio para cadastrar e atualizar produtos.
- **EmailNotificationServiceImpl.java**:
  - [ ] Implementar o método `enviarNotificacao` para imprimir uma mensagem no console.
- **PedidoServiceImpl.java**:
  - [ ] Implementar todas as regras de negócio para o ciclo de vida de um pedido (criar, adicionar/remover itens, finalizar, pagar, entregar).
  - [ ] Utilizar o `NotificationService` para notificar o cliente nas etapas apropriadas.

### Controller (`com.adatech.ecommerce.controller`)
- **ClienteController.java**:
  - [ ] Implementar os métodos para chamar o `ClienteService`.
- **ProdutoController.java**:
  - [ ] Implementar os métodos para chamar o `ProdutoService`.
- **PedidoController.java**:
  - [ ] Implementar os métodos para chamar o `PedidoService`.

### View (`com.adatech.ecommerce.view`)
- **ClienteView.java**:
  - [ ] Implementar o menu de clientes e os métodos para coletar dados do usuário.
- **ProdutoView.java**:
  - [ ] Implementar o menu de produtos e os métodos para coletar dados do usuário.
- **PedidoView.java**:
  - [ ] Implementar o menu de pedidos e os submenus para gerenciar um pedido.
- **MainView.java**:
  - [ ] Implementar o menu principal que direciona para as outras views.

### Ponto de Entrada
- **Main.java**:
  - [ ] Criar o método `main` para instanciar e iniciar a `MainView`.

---

## Plano de Ataque (Sugestão para 8 dias)

Este plano divide o desenvolvimento em etapas lógicas para garantir que o projeto seja concluído a tempo.

**Dia 1-2: Estrutura e Modelagem (Fundação)**
1.  **Implementar as classes do pacote `model`**: Preencher todos os atributos, construtores e métodos das classes `Cliente`, `Produto`, `ItemVenda`, `Pedido` e `StatusPedido`.
2.  **Testes Unitários (Básico)**: Se o tempo permitir, criar testes simples para as classes de modelo para garantir que os getters e setters funcionam como esperado.

**Dia 3-4: Camada de Dados e Serviços (Lógica Principal)**
1.  **Implementar os Repositórios**: Codificar as classes `...Impl` no pacote `repository`. Comece pelo `ClienteRepositoryImpl` e `ProdutoRepositoryImpl`, pois são mais simples.
2.  **Implementar os Serviços**: Codificar as classes `...Impl` no pacote `service`.
    - Comece pelo `ClienteServiceImpl` e `ProdutoServiceImpl`.
    - Implemente o `EmailNotificationServiceImpl`.
    - Por último, implemente o `PedidoServiceImpl`, que é o mais complexo e depende dos outros.

**Dia 5-6: Controladores e Interface do Usuário (Interação)**
1.  **Implementar os Controladores**: Preencher a lógica dos métodos nas classes de `controller`. Eles são simples e apenas repassam as chamadas para os serviços.
2.  **Implementar as Views**: Esta é a parte que interage com o usuário.
    - Comece pela `ClienteView` e `ProdutoView`.
    - Implemente a `PedidoView`, que será a mais complexa.
    - Finalize com a `MainView` para amarrar tudo.

**Dia 7: Integração e Testes Finais**
1.  **Criar a classe `Main`**: Crie o ponto de entrada da aplicação que inicia a `MainView`.
2.  **Testes de Fluxo**: Execute a aplicação e teste todos os fluxos completos:
    - Cadastrar um cliente e um produto.
    - Criar um pedido para esse cliente.
    - Adicionar o produto ao pedido.
    - Finalizar, pagar e entregar o pedido.
    - Testar as validações (ex: não finalizar pedido sem itens).
3.  **Revisão e Refatoração**: Revise o código em busca de bugs, melhorias e trechos que possam ser simplificados.

**Dia 8: Documentação e Entrega**
1.  **Revisar o `README.md`**: Atualize o README com qualquer informação adicional relevante.
2.  **Limpar o Código**: Remova comentários de `// TODO:` que já foram concluídos.
3.  **Preparar para a Entrega**: Garantir que o projeto esteja compilando e executando sem erros.

