package laricaco;

import java.util.ArrayList;
import java.util.List;

import laricaco.Exceptions.ProdutoNaoEncontradoException;
import laricaco.Exceptions.QuantidadeInvalidaException;
import laricaco.Exceptions.SaldoInsuficienteException;

public class SistemaGerenciamento {
    private double taxa;
    private double saldo;
    private List<Produto> produtos;
    private List<ItemVenda> vendas;
    private List<Usuario> usuarios;

    private int contagemId = 1;
    private String login;
    private String senha;

    public SistemaGerenciamento(double taxa, double saldo, String login, String senha) {
        this.taxa = taxa;
        this.saldo = saldo;
        this.login = login;
        this.senha = senha;

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean verificarSenha(String senha) {
        if (senha == this.senha)
            return true;
        else
            return false;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Usuario criarUsuario(String login, String senha, double saldo) {
        Usuario usuario = new Usuario(login, senha, saldo);
        usuarios.add(usuario);
        return usuario;
    }

    public void realizarVenda(Usuario cliente, Vendedor vendedor) throws Exception {
        Carrinho c = cliente.getCarrinho();

        if (cliente.getSaldo() < c.calcularTotal())
            throw new SaldoInsuficienteException();

        for (ItemVenda i : c.getItens()) {
            double preco = i.getTotal();

            cliente.retirarSaldo(preco);

            this.saldo += preco * this.taxa;
            vendedor.adicionarSaldo(preco * (1 - this.taxa));

            i.getProduto().retirarEstoque(i.getQuantidade());

            this.vendas.add(i);
            vendedor.adicionarItemVenda(i);
        }
        c.setStatus(true);
    }

    public Doce cadastrarDoce(String nome, double preco, String descricao, int estoque, Vendedor vendedor)
            throws Exception {

        if (estoque <= 0)
            throw new QuantidadeInvalidaException();

        Doce d = new Doce(contagemId, nome, preco, descricao, estoque);
        d.setVendedor(vendedor);
        this.produtos.add(d);
        vendedor.adicionarProduto(d);
        contagemId += 1;
        return d;
    }

    public Salgado cadastrarSalgado(String nome, double preco, String descricao, int estoque, Vendedor vendedor)
            throws Exception {

        if (estoque <= 0)
            throw new QuantidadeInvalidaException();

        Salgado s = new Salgado(contagemId, nome, preco, descricao, estoque);
        s.setVendedor(vendedor);
        this.produtos.add(s);
        vendedor.adicionarProduto(s);
        contagemId += 1;
        return s;
    }

    public Adesivo cadastrarAdesivo(String nome, double preco, String descricao, int estoque, Vendedor vendedor)
            throws Exception {

        if (estoque <= 0)
            throw new QuantidadeInvalidaException();

        Adesivo a = new Adesivo(contagemId, nome, preco, descricao, estoque, "pequeno");
        a.setVendedor(vendedor);
        this.produtos.add(a);
        vendedor.adicionarProduto(a);
        contagemId += 1;
        return a;
    }

    public void removerProduto(int id) throws Exception {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                produtos.remove(p);
                return;
            }
        }
        throw new ProdutoNaoEncontradoException();
    }

    public void removerProduto(String nome) throws Exception {
        for (Produto p : produtos) {
            if (p.getNome() == nome) {
                produtos.remove(p);
                return;
            }
        }
        throw new ProdutoNaoEncontradoException();
    }

    public Vendedor virarVendedor(Usuario usuario, String senha) {
        if (usuario.verificarSenha(senha)) {
            Vendedor vendedor = new Vendedor(usuario.getLogin(), senha, usuario.getSaldo());
            this.usuarios.remove(usuario);
            this.usuarios.add(vendedor);
            return vendedor;
        }
        return null;
    }

    public int verificarLogin(String login) {
        if (this.login == login)
            return 2;
        else {
            for (Usuario u : usuarios) {
                if (u.getLogin() == login)
                    return 1;
            }
        }
        return 0;
    }

    // metodo para teste
    public void imprimirProdutos() {
        System.out.println("Produtos do sistema:");
        for (Produto p : this.produtos) {
            System.out.println(p.getId() + " - " + p.getNome() + " (" + p.getEstoque() + ")");
        }
        System.out.println();
    }
}
