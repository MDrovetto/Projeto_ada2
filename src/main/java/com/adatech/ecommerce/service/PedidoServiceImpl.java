package com.adatech.ecommerce.service;

import com.adatech.ecommerce.exception.PedidoVazioException;
import com.adatech.ecommerce.exception.RegraDeNegocioException;
import com.adatech.ecommerce.exception.RecursoNaoEncontradoException;
import com.adatech.ecommerce.model.*;
import com.adatech.ecommerce.repository.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final NotificationService notificationService;
    private final CupomService cupomService;

    public PedidoServiceImpl(
            PedidoRepository pedidoRepository,
            ClienteRepository clienteRepository,
            ProdutoRepository produtoRepository,
            NotificationService notificationService,
            CupomService cupomService) {

        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.produtoRepository = produtoRepository;
        this.notificationService = notificationService;
        this.cupomService = cupomService;
    }

    @Override
    public Pedido criarPedido(String cpfCliente) {
        Cliente cliente = clienteRepository.buscarPorCpf(cpfCliente);

        if (cliente == null) {
            throw new RecursoNaoEncontradoException("Cliente com CPF '" + cpfCliente + "' não encontrado para criar o pedido.");
        }

        Pedido novoPedido = new Pedido(0, cliente, LocalDate.now());
        return pedidoRepository.salvar(novoPedido);
    }

    @Override
    public boolean adicionarItem(int pedidoId, int produtoId, int quantidade, BigDecimal precoVenda) {

        if (quantidade <= 0) {
            throw new RegraDeNegocioException("A quantidade do item deve ser positiva.");
        }
        if (precoVenda == null || precoVenda.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("O preço de venda deve ser positivo.");
        }

        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        Produto produto = produtoRepository.buscarPorId(produtoId);
        if (produto == null) {
            throw new RecursoNaoEncontradoException("Produto ID " + produtoId + " não encontrado.");
        }

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraDeNegocioException("Não é possível adicionar itens a um pedido com status '" + pedido.getStatus() + "'.");
        }

        ItemVenda item = new ItemVenda(produto, quantidade, precoVenda);
        pedido.adicionarItem(item);
        pedidoRepository.salvar(pedido);
        return true;
    }

    @Override
    public boolean removerItem(int pedidoId, int produtoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraDeNegocioException("Não é possível remover itens de um pedido com status '" + pedido.getStatus() + "'.");
        }

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

        throw new RecursoNaoEncontradoException("Item com Produto ID " + produtoId + " não encontrado no pedido.");
    }

    @Override
    public boolean alterarQuantidadeItem(int pedidoId, int produtoId, int novaQuantidade) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }
        if (novaQuantidade <= 0) {
            throw new RegraDeNegocioException("A nova quantidade deve ser maior que zero.");
        }

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraDeNegocioException("Não é possível alterar itens de um pedido com status '" + pedido.getStatus() + "'.");
        }

        ItemVenda itemParaAlterar = null;
        for (ItemVenda item : pedido.getItens()) {
            if (item.getProduto().getId() == produtoId) {
                itemParaAlterar = item;
                break;
            }
        }

        if (itemParaAlterar != null) {
            pedido.removerItem(itemParaAlterar);

            BigDecimal precoVendaOriginal = itemParaAlterar.getPrecoVenda();
            ItemVenda novoItem = new ItemVenda(itemParaAlterar.getProduto(), novaQuantidade, precoVendaOriginal);
            pedido.adicionarItem(novoItem);

            pedidoRepository.salvar(pedido);
            return true;
        }

        throw new RecursoNaoEncontradoException("Item com Produto ID " + produtoId + " não encontrado no pedido.");
    }

    @Override
    public boolean aplicarCupom(int pedidoId, String codigoCupom) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getStatus() != StatusPedido.ABERTO) {
            throw new RegraDeNegocioException("Não é possível aplicar cupom a um pedido que não está ABERTO.");
        }

        if (pedido.getItens().isEmpty()) {
            throw new RegraDeNegocioException("Não é possível aplicar cupom a um pedido vazio.");
        }

        if (pedido.getCupomAplicado() != null) {
            throw new RegraDeNegocioException("Um cupom já está aplicado neste pedido (" + pedido.getCupomAplicado().getCodigo() + ").");
        }

        BigDecimal valorTotalPedido = pedido.getValorTotal();

        try {
            BigDecimal novoValorTotal = cupomService.aplicarDesconto(codigoCupom, valorTotalPedido);
            Cupom cupom = cupomService.buscarPorCodigo(codigoCupom);
            pedido.aplicarCupom(cupom, novoValorTotal);

            pedidoRepository.salvar(pedido);
            return true;

        } catch (RecursoNaoEncontradoException | RegraDeNegocioException ex) {
            throw ex;
        }
    }

    @Override
    public boolean finalizarPedido(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getItens().isEmpty()) {
            throw new PedidoVazioException("Não é possível finalizar um pedido que não contém itens.");
        }

        BigDecimal valorFinal = pedido.getTotalComDesconto();

        if (valorFinal == null || valorFinal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("O valor total do pedido com desconto (" + valorFinal + ") é zero ou negativo. Verifique os itens ou o desconto.");
        }

        if (pedido.getCupomAplicado() != null) {
            try {
                cupomService.marcarComoUsado(pedido.getCupomAplicado().getId());
            } catch (Exception ex) {
                throw new RegraDeNegocioException("Falha ao marcar cupom como usado: " + ex.getMessage());
            }
        }

        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);
        pedidoRepository.salvar(pedido);

        notificationService.enviarNotificacao(pedido.getCliente(), "Seu pedido foi finalizado (Total: R$" + valorFinal + ") e está aguardando pagamento! ID: " + pedido.getId());
        return true;
    }

    @Override
    public boolean pagarPedido(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new RegraDeNegocioException("O pedido não pode ser pago pois o status atual é '" + pedido.getStatus() + "'. Deve estar AGUARDANDO_PAGAMENTO.");
        }

        pedido.setStatus(StatusPedido.PAGO);
        pedidoRepository.salvar(pedido);

        notificationService.enviarNotificacao(pedido.getCliente(), "O pagamento do seu pedido foi confirmado! ID: " + pedido.getId());
        return true;
    }

    @Override
    public boolean entregarPedido(int pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);

        if (pedido == null) {
            throw new RecursoNaoEncontradoException("Pedido ID " + pedidoId + " não encontrado.");
        }

        if (pedido.getStatus() != StatusPedido.PAGO) {
            throw new RegraDeNegocioException("O pedido não pode ser entregue pois o status atual é '" + pedido.getStatus() + "'. Deve estar PAGO.");
        }

        pedido.setStatus(StatusPedido.FINALIZADO);
        pedidoRepository.salvar(pedido);

        notificationService.enviarNotificacao(pedido.getCliente(), "Seu pedido foi entregue! Obrigado pela compra. ID: " + pedido.getId());
        return true;
    }

    @Override
    public List<Pedido> listarPedidos() {
        return pedidoRepository.listarTodos();
    }

    @Override
    public Pedido buscarPedidoPorId(int id) {
        return pedidoRepository.buscarPorId(id);
    }

    @Override
    public List<Pedido> buscarPedidosPorCliente(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            throw new RegraDeNegocioException("CPF é obrigatório para a busca.");
        }
        return pedidoRepository.buscarPorCliente(cpf);
    }
}