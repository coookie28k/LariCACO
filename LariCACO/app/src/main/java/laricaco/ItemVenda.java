package laricaco;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ItemVenda {
    private double total;
    private Produto produto;
    private int quantidade;
    private final LocalDate data;
    static final DateTimeFormatter BR_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ItemVenda(LocalDate data, Produto produto, int quantidade) {
        this.data = data;
        this.total = produto.getPreco() * quantidade;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDataFormatada() {
        return data.format(BR_FORMAT);
    }

    public double getTotal() {
        Promocao promo = this.produto.getPromocao();
        if (promo != null && this.quantidade == promo.getUnidades())
            return promo.getPreco();
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
