package laricaco;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


/**
 * Representa um item de venda no carrinho, contendo o produto, a quantidade
 * e a data da adição. Calcula o total com base no preço e possíveis promoções.
 */
public class ItemVenda {
    /**
     * Valor total do item (considerando preço unitário e quantidade).
     */
    private double total;

    /**
     * Produto associado a este item de venda.
     */
    private Produto produto;

    /**
     * Quantidade do produto neste item.
     */
    
    private int quantidade;

    /**
     * Data em que o item foi adicionado ao carrinho.
     */
    private final LocalDate data;
    
    /**
     * Formatador de datas no padrão brasileiro (dd/MM/yyyy).
     */
    static final DateTimeFormatter BR_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Construtor da classe ItemVenda.
     *
     * @param data       data da adição do item ao carrinho
     * @param produto    produto associado ao item
     * @param quantidade quantidade do produto
     */
    public ItemVenda(LocalDate data, Produto produto, int quantidade) {
        this.data = data;
        this.total = produto.getPreco() * quantidade;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    /**
     * Retorna a data da adição do item ao carrinho.
     *
     * @return data do item
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Retorna a data formatada no padrão brasileiro (dd/MM/yyyy).
     *
     * @return data formatada como string
     */
    public String getDataFormatada() {
        return data.format(BR_FORMAT);
    }

    /**
     * Retorna o valor total do item.
     * Caso haja uma promoção ativa e a quantidade atenda aos requisitos,
     * o preço promocional é retornado.
     *
     * @return valor total (com promoção, se aplicável)
     */
    public double getTotal() {
        Promocao promo = this.produto.getPromocao();
        if (promo != null && this.quantidade == promo.getUnidades())
            return promo.getPreco();
        return total;
    }

     /**
     * Define o valor total do item.
     *
     * @param total novo valor total
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Retorna o produto associado a este item.
     *
     * @return produto do item
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Define o produto associado a este item.
     *
     * @param produto novo produto
     */
    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    /**
     * Retorna a quantidade do produto neste item.
     *
     * @return quantidade do item
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Define a quantidade do produto neste item.
     *
     * @param quantidade nova quantidade
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
