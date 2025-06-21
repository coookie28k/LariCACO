package laricaco;

import java.util.ArrayList;
import java.util.List;

public class Vendedor extends Usuario {
    private List<ItemVenda> minhasVendas = new ArrayList<>();
    private List<Produto> meusProdutos = new ArrayList<>();

    Vendedor(String login, String senha, double saldo, Carrinho carrinho, List<ItemVenda> minhasVendas,
            List<Produto> meusProdutos) {
        super(login, senha, saldo, carrinho);
        this.minhasVendas = minhasVendas;
        this.meusProdutos = meusProdutos;
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
}
