package laricaco;

import java.util.ArrayList;
import java.util.List;

public class SistemaGerenciamento {
    private double taxa;
    private double saldo;
    private List<Produto> produtos;
    private List<ItemVenda> vendas;
    private List<Usuario> usuarios = new ArrayList<>();

    SistemaGerenciamento(double taxa, double saldo, List<Produto> produtos, List<ItemVenda> vendas) {
        this.taxa = taxa;
        this.saldo = saldo;
        this.produtos = produtos;
        this.vendas = vendas;
    }

    public double getTaxa() {
        return taxa;
    }

    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<ItemVenda> getVendas() {
        return vendas;
    }

    public void setVendas(List<ItemVenda> vendas) {
        this.vendas = vendas;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario criarUsuario(String login, String senha, double saldo) {
        Usuario usuario = new Usuario(login, senha, saldo, null);
        usuarios.add(usuario);
        return usuario;
    }

    public void realizarVenda(Usuario cliente, Usuario vendedor) {
        Carrinho c = cliente.getCarrinho();
        for (ItemVenda i : c.getItens()) {
            double preco = i.getTotal();

            cliente.retiraSaldo(preco);

            this.saldo += preco * this.taxa;
            vendedor.adicionaSaldo(preco * (1 - this.taxa));

            i.getProduto().retiraEstoque(i.getQuantidade());
        }
    }
}
