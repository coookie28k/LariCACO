package laricaco;

import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario {
    private List<ItemVenda> minhasVendas;
    private List<Produto> meusProdutos;

    public Vendedor(String login, String senha, double saldo) {
        super(login, senha, saldo);
        this.minhasVendas = new ArrayList<>();
        this.meusProdutos = new ArrayList<>();
    }

    public List<ItemVenda> getMinhasVendas() {
        return minhasVendas;
    }

    public void setMinhasVendas(List<ItemVenda> minhasVendas) {
        this.minhasVendas = minhasVendas;
    }

    public List<Produto> getMeusProdutos() {
        return meusProdutos;
    }

    public void setMeusProdutos(List<Produto> meusProdutos) {
        this.meusProdutos = meusProdutos;
    }

    public void adicionarProduto(Produto produto) {
        this.meusProdutos.add(produto);
    }

    public void removerProduto(Produto produto) {
        this.meusProdutos.remove(produto);
    }

    public void adicionarItemVenda(ItemVenda itemVenda) {
        this.minhasVendas.add(itemVenda);
    }

    // método para teste
    public void imprimirVendas() {
        System.out.println("Vendas de " + this.getLogin() + ":");
        for (ItemVenda i : this.minhasVendas) {
            System.out.println(i.getProduto().getNome() + " (" + i.getQuantidade() + ") - " + i.getDataFormatada());
        }
        System.out.println();
    }

    public void imprimirProdutos() {
        System.out.println("Produtos de " + this.getLogin() + ":");
        for (Produto i : this.meusProdutos) {
            System.out.println(i.getNome() + " (" + i.getEstoque() + ")");
        }
        System.out.println();
    }
}
