package laricaco;

import java.util.ArrayList;
import java.util.List;

public class SistemaGerenciamento {
    private double taxa;
    private double saldo;
    private List<Produto> produtos;
    private List<ItemVenda> vendas;
    private List<Usuario> usuarios;

    private int contagemId = 1;

    public SistemaGerenciamento(double taxa, double saldo) {
        this.taxa = taxa;
        this.saldo = saldo;
        this.produtos = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
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
        Usuario usuario = new Usuario(login, senha, saldo);
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

            this.vendas.add(i);
        }
        c.setStatus(true);
    }

    public Doce cadastrarDoce(String nome, double preco, String descricao, int estoque, Vendedor vendedor) {
        Doce d = new Doce(contagemId, nome, preco, descricao, estoque);
        d.setVendedor(vendedor);
        this.produtos.add(d);
        vendedor.adicionarProduto(d);
        return d;
    }

    public Salgado cadastrarSalgado(String nome, double preco, String descricao, int estoque, Vendedor vendedor) {
        Salgado s = new Salgado(contagemId, nome, preco, descricao, estoque);
        s.setVendedor(vendedor);
        this.produtos.add(s);
        vendedor.adicionarProduto(s);
        return s;
    }

    public Adesivo cadastrarAdesivo(String nome, double preco, String descricao, int estoque, Vendedor vendedor) {
        Adesivo a = new Adesivo(contagemId, nome, preco, descricao, estoque, "pequeno");
        a.setVendedor(vendedor);
        this.produtos.add(a);
        vendedor.adicionarProduto(a);
        return a;
    }

    public void removerProduto(int id) {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                produtos.remove(p);
                return;
            }
        }
        // adicionar erro se não encontrar o produto
    }

    public Vendedor virarVendedor(Usuario usuario, String senha) {
        if (usuario.verificaSenha(senha)) {
            Vendedor vendedor = new Vendedor(usuario.getLogin(), senha, usuario.getSaldo());
            this.usuarios.remove(usuario);
            this.usuarios.add(vendedor);
            return vendedor;
        }
        return null;
    }
}
