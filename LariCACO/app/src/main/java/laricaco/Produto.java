package laricaco;

import java.util.ArrayList;
import java.util.List;

public class Produto {
    private String nome;
    private double preco;
    private String descricao;
    private int estoque;

    private Vendedor vendedor;
    private List<Tag> tagProduto;
    private Promocao promocao;

    public Produto(String nome, double preco, String descricao, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.estoque = estoque;

        this.vendedor = null;
        this.tagProduto = new ArrayList<>();
        this.promocao = null;
    }

    public List<Tag> getTag() {
        return tagProduto;
    }

    public void setTag(List<Tag> tags) {
        this.tagProduto = tags;
    }

    public void adicionarTag(Tag tag) {
        this.tagProduto.add(tag);
    }

    public void adicionarTag(String descricao) {
        Tag tag = new Tag(descricao);
        this.tagProduto.add(tag);
    }

    public void removerTag(Tag tag) {
        this.tagProduto.remove(tag);
    }

    public void removerTag(String descricao) {
        Tag tag = null;
        for (Tag t : this.tagProduto)
            if (t.getTag() == descricao)
                tag = t;
        if (tag != null)
            this.tagProduto.remove(tag);
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public void retirarEstoque(int quantidade) {
        this.estoque -= quantidade;
    }

    public void adicionarEstoque(int quantidade) {
        this.estoque += quantidade;
    }

    public Promocao getPromocao() {
        return promocao;
    }

    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
    }
}
