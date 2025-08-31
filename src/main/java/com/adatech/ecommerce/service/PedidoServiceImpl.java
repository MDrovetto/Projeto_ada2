package main.java.com.adatech.ecommerce.service;

import main.java.com.adatech.ecommerce.model.Pedido;
import main.java.com.adatech.ecommerce.repository.ClienteRepository;
import main.java.com.adatech.ecommerce.repository.ClienteRepositoryImpl;
import main.java.com.adatech.ecommerce.repository.PedidoRepository;
import main.java.com.adatech.ecommerce.repository.PedidoRepositoryImpl;
import main.java.com.adatech.ecommerce.repository.ProdutoRepository;
import main.java.com.adatech.ecommerce.repository.ProdutoRepositoryImpl;

import java.util.List;

/**.
 * Implementação dos serviços relacionados a Pedidos.
 * Contém a lógica de negócio para a entidade Pedido.
 * TODO:
 *  - Implementar os métodos da interface PedidoService.
 *  - No construtor, instanciar os repositórios necessários (Pedido, Cliente, Produto) e o serviço de notificação.
 *  - Implementar as regras de negócio para cada operação, como:
 *    - criarPedido: verificar se o cliente existe.
 *    - adicionarItem: verificar se o pedido e o produto existem, e se o pedido está com status "ABERTO".
 *    - removerItem: verificar se o pedido está com status "ABERTO".
 *    - alterarQuantidadeItem: verificar se o pedido está com status "ABERTO".
 *    - finalizarPedido: verificar se o pedido tem itens e valor > 0, alterar status e notificar cliente.
 *    - pagarPedido: verificar se o status é "AGUARDANDO_PAGAMENTO", alterar status e notificar cliente.
 *    - entregarPedido: verificar se o status é "PAGO", alterar status e notificar cliente.
 */
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    // private final NotificationService notificationService; // A ser criado

    public PedidoServiceImpl() {
        this.pedidoRepository = new PedidoRepositoryImpl();
        this.clienteRepository = new ClienteRepositoryImpl();
        this.produtoRepository = new ProdutoRepositoryImpl();
        // this.notificationService = new EmailNotificationServiceImpl(); // A ser criado
    }

    @Override
    public Pedido criarPedido(String cpfCliente) {
        // TODO: Implementar a lógica de criação de pedido
        return null;
    }

    @Override
    public boolean adicionarItem(int pedidoId, int produtoId, int quantidade, double precoVenda) {
        // TODO: Implementar a lógica para adicionar item
        return false;
    }

    @Override
    public boolean removerItem(int pedidoId, int produtoId) {
        // TODO: Implementar a lógica para remover item
        return false;
    }

    @Override
    public boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade) {
        // TODO: Implementar a lógica para alterar a quantidade do item
        return false;
    }

    @Override
    public boolean finalizarPedido(int pedidoId) {
        // TODO: Implementar a lógica para finalizar o pedido
        return false;
    }

    @Override
    public boolean pagarPedido(int pedidoId) {
        // TODO: Implementar a lógica para pagar o pedido
        return false;
    }

    @Override
    public boolean entregarPedido(int pedidoId) {
        // TODO: Implementar a lógica para entregar o pedido
        return false;
    }

    @Override
    public List<Pedido> listarPedidos() {
        // TODO: Implementar a lógica para listar os pedidos
        return null;
    }

    @Override
    public Pedido buscarPedidoPorId(int id) {
        // TODO: Implementar a lógica de busca por ID
        return null;
    }
}

