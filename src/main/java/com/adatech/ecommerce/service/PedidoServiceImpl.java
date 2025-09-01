package com.adatech.ecommerce.service;

import com.adatech.ecommerce.model.Pedido;
import com.adatech.ecommerce.repository.ClienteRepository;
import com.adatech.ecommerce.repository.ClienteRepositoryImpl;
import com.adatech.ecommerce.repository.PedidoRepository;
import com.adatech.ecommerce.repository.PedidoRepositoryImpl;
import com.adatech.ecommerce.repository.ProdutoRepository;
import com.adatech.ecommerce.repository.ProdutoRepositoryImpl;
import java.math.BigDecimal;
import java.time.LocalDate;
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
    private final NotificationService notificationService; // A ser criado

    public PedidoServiceImpl() {
        this.pedidoRepository = new PedidoRepositoryImpl();
        this.clienteRepository = new ClienteRepositoryImpl();
        this.produtoRepository = new ProdutoRepositoryImpl();
        this.notificationService = new EmailNotificationServiceImpl(); // A ser criado
    }

    @Override
    public Pedido criarPedido(String cpfCliente) {
        // TODO: Implementar a lógica de criação de pedido
        // Lógica de negócio: verifica se o cliente existe.
        Cliente cliente = clienteRepository.buscarPorCpf(cpfCliente);
        if (cliente == null) {
            System.err.println("Erro: Cliente não encontrado para criar o pedido.");
            return null;
        }

        // TODO: Em uma implementação real, o ID do pedido seria gerado pelo banco de dados.
        // Aqui, um ID temporário é usado para o exemplo.
        Pedido novoPedido = new Pedido(0, cliente, LocalDate.now());

        return pedidoRepository.salvar(novoPedido);
    }

    @Override
    public boolean adicionarItem(int pedidoId, int produtoId, int quantidade, double precoVenda) {
        // TODO: Implementar a lógica para adicionar item
        // Busca o pedido e o produto nos repositórios.
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        Produto produto = produtoRepository.buscarPorId(produtoId);

        // Valida se o pedido e o produto foram encontrados.
        if (pedido == null || produto == null) {
            System.err.println("Erro: Pedido ou Produto não encontrado.");
            return false;
        }

        // Valida se o status do pedido é "ABERTO" para permitir a adição do item.
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            System.err.println("Erro: Não é possível adicionar itens a um pedido que não está em status ABERTO.");
            return false;
        }

        // Converte o 'double' para 'BigDecimal' de forma segura.
        BigDecimal precoVendaBigDecimal = BigDecimal.valueOf(precoVenda);

        // Cria o novo ItemVenda, usando o construtor com o BigDecimal.
        ItemVenda item = new ItemVenda(produto, quantidade, precoVendaBigDecimal);

        // Adiciona o item ao pedido e salva a alteração no repositório.
        pedido.adicionarItem(item);
        pedidoRepository.salvar(pedido);
        return true;
    }

    @Override
    public boolean removerItem(int pedidoId, int produtoId) {
        // TODO: Implementar a lógica para remover item
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId); // instância temporariamente um pedido para ver se existe na lista de pedidos

        if (pedido == null) {
            return false;
        }

        // Lógica de negócio: verifica se o pedido está com status "ABERTO".
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            return false;
        }

        // Busca o item de venda correspondente ao produtoId.
        ItemVenda itemParaRemover = null;
        for (ItemVenda item : pedido.getItens()) {
            if (item.getProduto().getId() == produtoId) {
                itemParaRemover = item;
                break;
            }
        }

        if (itemParaRemover != null) {
            pedido.removerItem(itemParaRemover);
            pedidoRepository.salvar(pedido);
            return true;
        }
        return false;
    }

    @Override
    public boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade) {
        // TODO: Implementar a lógica para alterar a quantidade do item
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        // 1. Validação inicial: verifica se o pedido existe e se a quantidade é válida.
        if (pedido == null || novaQuantidade <= 0) {
            System.err.println("Erro: Pedido não encontrado ou quantidade inválida.");
            return false;
        }

        // 2. Lógica de negócio: verifica se o pedido está com status "ABERTO".
        if (pedido.getStatus() != StatusPedido.ABERTO) {
            System.err.println("Erro: Não é possível alterar itens de um pedido que não está ABERTO.");
            return false;
        }

        // 3. Encontra o item que será alterado.
        ItemVenda itemParaAlterar = null;
        for (ItemVenda item : pedido.getItens()) {
            if (item.getProduto().getId() == produtoId) {
                itemParaAlterar = item;
                break;
            }
        }

        // 4. Se o item for encontrado, procede com a alteração.
        if (itemParaAlterar != null) {
            // Remove o item antigo do pedido.
            pedido.removerItem(itemParaAlterar);

            // Obtém o preço de venda do item original para o novo item.
            BigDecimal precoVendaOriginal = itemParaAlterar.getPrecoVenda();

            // Cria um novo item com a quantidade e o preço de venda corretos.
            ItemVenda novoItem = new ItemVenda(itemParaAlterar.getProduto(), novaQuantidade, precoVendaOriginal);

            // Adiciona o novo item e salva o pedido.
            pedido.adicionarItem(novoItem);
            pedidoRepository.salvar(pedido);
            System.out.println("Quantidade do item alterada com sucesso.");
            return true;
        }

        System.err.println("Erro: Item não encontrado no pedido.");
        return false;

    }

    @Override
    public boolean finalizarPedido(int pedidoId) {
        // TODO: Implementar a lógica para finalizar o pedido
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            return false;
        }

        // Lógica de negócio: verifica se o pedido tem itens e valor > 0.
        if (pedido.getItens().isEmpty() || pedido.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            System.err.println("Erro: Não é possível finalizar um pedido vazio.");
            return false;
        }

        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoRepository.salvar(pedido);
        notificationService.enviarNotificacao(pedido.getCliente(), "Seu pedido foi finalizado com sucesso!");
        return true;
    }

    @Override
    public boolean pagarPedido(int pedidoId) {
        // TODO: Implementar a lógica para pagar o pedido
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            return false;
        }

        // Lógica de negócio: verifica se o status atual é "AGUARDANDO_PAGAMENTO".
        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            System.err.println("Erro: O pedido não está aguardando pagamento.");
            return false;
        }

        pedido.setStatus(StatusPedido.PAGO);
        pedidoRepository.salvar(pedido);
        notificationService.enviarNotificacao(pedido.getCliente(), "O pagamento do seu pedido foi confirmado!");
        return true;
    }

    @Override
    public boolean entregarPedido(int pedidoId) {
        // TODO: Implementar a lógica para entregar o pedido
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            return false;
        }

        // Lógica de negócio: verifica se o status atual é "PAGO".
        if (pedido.getStatus() != StatusPedido.PAGO) {
            System.err.println("Erro: O pedido não está pago para ser entregue.");
            return false;
        }

        pedido.setStatus(StatusPedido.FINALIZADO);
        pedidoRepository.salvar(pedido);
        notificationService.enviarNotificacao(pedido.getCliente(), "Seu pedido foi entregue!");
        return true;
    }

    @Override
    public List<Pedido> listarPedidos() {
        // TODO: Implementar a lógica para listar os pedidos
        // Delega a responsabilidade de listar os pedidos para o repositório(pasta repository).
        return pedidoRepository.listarTodos();
    }

    @Override
    public Pedido buscarPedidoPorId(int id) {
        // TODO: Implementar a lógica de busca por ID
        // Delega a responsabilidade de buscar o pedido para o repositório(pasta repository).
        return pedidoRepository.buscarPorId(id);
    }
}

