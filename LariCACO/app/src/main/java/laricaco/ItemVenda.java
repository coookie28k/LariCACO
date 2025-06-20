package laricaco;

import java.time.LocalDate;

public class ItemVenda {
    private LocalDate data;
    private double total;
    private Produto produto;
    private int quantidade;

    ItemVenda(LocalDate data, Produto produto, int quantidade) {
        this.data = data;
        this.total = produto.getPreco() * quantidade;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getTotal() {
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
