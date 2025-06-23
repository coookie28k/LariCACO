package laricaco;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import laricaco.Exceptions.LoginJaExistenteException;
import laricaco.Exceptions.ProdutoNaoEncontradoException;
import laricaco.Exceptions.QuantidadeInvalidaException;
import laricaco.Exceptions.SaldoInsuficienteException;
import laricaco.Filtros.ItemVendaPorDataFiltro;
import laricaco.Filtros.ProdutoPorTagFiltro;
import laricaco.Filtros.ProdutoPorTipoFiltro;

/**
 * Classe que representa o sistema de gerenciamento de produtos, vendas,
 * usuários e promoções.
 */
public class SistemaGerenciamento implements Serializable {

    private static final long serialVersionUID = 1L; // <- Versão de serialização

    /** Taxa de comissão sobre as vendas. */
    private double taxa;

    /** Saldo acumulado da aplicação. */
    private double saldo;

    /** Lista de produtos cadastrados no sistema. */
    private List<Produto> produtos;

    /** Lista de vendas realizadas no sistema. */
    private List<ItemVenda> vendas;

    /** Lista de usuários cadastrados. */
    private List<Usuario> usuarios;

    /** Contador para geração de IDs únicos. */
    private int contagemId = 1;

    /** Login do sistema administrativo. */
    private String login;

    /** Senha do sistema administrativo. */
    private String senha;

    /** Instância única do sistema */
    private static SistemaGerenciamento instancia;

    /**
     * Construtor da classe SistemaGerenciamento.
     * 
     * @param taxa  taxa de comissão
     * @param saldo saldo inicial do sistema
     * @param login login administrativo
     * @param senha senha administrativa
     */
    private SistemaGerenciamento(double taxa, double saldo, String login, String senha) {
        this.taxa = taxa;
        this.saldo = saldo;
        this.login = login;
        this.senha = senha;

        this.produtos = new ArrayList<>();
        this.vendas = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    /**
     * Retorna a instância única do sistema (Singleton).
     */
    public static SistemaGerenciamento getInstance(double taxa, double saldo, String login, String senha) {
        if (instancia == null) {
            instancia = new SistemaGerenciamento(taxa, saldo, login, senha);
        }
        return instancia;
    }

    /**
     * Define uma instância já existente (usado após carregar de arquivo).
     */
    public static void setInstancia(SistemaGerenciamento sistema) {
        instancia = sistema;
    }

    /**
     * Retorna a taxa do sistema de gerenciamento.
     *
     * @return a taxa aplicada nas vendas.
     */
    public double getTaxa() {
        return taxa;
    }

    /**
     * Define a taxa do sistema de gerenciamento.
     *
     * @param taxa nova taxa a ser aplicada nas vendas.
     */
    public void setTaxa(double taxa) {
        this.taxa = taxa;
    }

    /**
     * Retorna o saldo da conta do sistema.
     *
     * @return o saldo atual.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Define o saldo da conta do sistema.
     *
     * @param saldo novo saldo a ser definido.
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Retorna a lista de produtos cadastrados.
     *
     * @return lista de produtos.
     */
    public List<Produto> getProdutos() {
        return produtos;
    }

    /**
     * Define a lista de produtos do sistema.
     *
     * @param produtos nova lista de produtos.
     */
    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    /**
     * Retorna a lista de vendas realizadas.
     *
     * @return lista de vendas.
     */
    public List<ItemVenda> getVendas() {
        return vendas;
    }

    /**
     * Define a lista de vendas realizadas.
     *
     * @param vendas nova lista de vendas.
     */
    public void setVendas(List<ItemVenda> vendas) {
        this.vendas = vendas;
    }

    /**
     * Retorna a lista de usuários do sistema.
     *
     * @return lista de usuários.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }


    /**
     * Define a lista de usuários do sistema.
     *
     * @param usuarios nova lista de usuários.
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Retorna o login do administrador do sistema.
     *
     * @return login atual.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o login do administrador do sistema.
     *
     * @param login novo login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Verifica se a senha fornecida corresponde à senha do sistema.
     * 
     * @param senha senha a ser verificada
     * @return true se for igual, false caso contrário
     */
    public boolean verificarSenha(String senha) {
        if (senha == this.senha)
            return true;
        else
            return false;
    }

    /**
     * Define a senha do administrador do sistema.
     *
     * @param senha nova senha.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Cria e adiciona um novo usuário ao sistema.
     * 
     * @param login login do novo usuário
     * @param senha senha do novo usuário
     * @param saldo saldo inicial
     * @return novo objeto Usuario criado
     * @throws LoginJaExistenteException se o login já estiver em uso
     */
    public Usuario criarUsuario(String login, String senha, double saldo) throws Exception {
        if (this.verificarLogin(login) != 0)
            throw new LoginJaExistenteException();

        Usuario usuario = new Usuario(login, senha, saldo);
        usuarios.add(usuario);
        return usuario;
    }

    /**
     * Realiza a venda dos produtos no carrinho de um cliente.
     * 
     * @param cliente usuário que está realizando a compra
     * @throws SaldoInsuficienteException se o cliente não tiver saldo suficiente
     */
    public void realizarVenda(Usuario cliente) throws Exception {
        Carrinho c = cliente.getCarrinho();

        if (cliente.getSaldo() < c.calcularTotal())
            throw new SaldoInsuficienteException();

        c.setStatus(true);
        for (ItemVenda i : c.getItens()) {
            Vendedor vendedor = i.getProduto().getVendedor();
            double preco = i.getTotal();

            cliente.retirarSaldo(preco);

            this.saldo += preco * this.taxa;
            vendedor.adicionarSaldo(preco * (1 - this.taxa));

            i.getProduto().retirarQuantidadeDoCarrinho(i.getQuantidade());
            i.getProduto().retirarEstoque(i.getQuantidade());

            this.vendas.add(i);
            vendedor.adicionarItemVenda(i);
        }
        c.limpar();
    }

    /**
     * Cadastra um novo doce no sistema.
     *
     * @param nome        nome do doce
     * @param preco       preço unitário do doce
     * @param descricao   descrição do doce
     * @param estoque     quantidade em estoque
     * @param vendedor    vendedor responsável pelo doce
     * @return objeto {@link Doce} criado
     * @throws QuantidadeInvalidaException se o estoque for negativo
     */
    public Doce cadastrarDoce(String nome, double preco, String descricao, int estoque, Vendedor vendedor)
            throws Exception {

        if (estoque < 0)
            throw new QuantidadeInvalidaException();

        Doce d = new Doce(contagemId, nome, preco, descricao, estoque);
        d.setVendedor(vendedor);
        this.produtos.add(d);
        vendedor.adicionarProduto(d);
        contagemId += 1;
        return d;
    }

    /**
     * Cadastra um novo salgado no sistema.
     *
     * @param nome        nome do salgado
     * @param preco       preço unitário do salgado
     * @param descricao   descrição do salgado
     * @param estoque     quantidade em estoque
     * @param vendedor    vendedor responsável pelo salgado
     * @return objeto {@link Salgado} criado
     * @throws QuantidadeInvalidaException se o estoque for negativo
     */
    public Salgado cadastrarSalgado(String nome, double preco, String descricao, int estoque, Vendedor vendedor)
            throws Exception {

        if (estoque < 0)
            throw new QuantidadeInvalidaException();

        Salgado s = new Salgado(contagemId, nome, preco, descricao, estoque);
        s.setVendedor(vendedor);
        this.produtos.add(s);
        vendedor.adicionarProduto(s);
        contagemId += 1;
        return s;
    }

    /**
     * Cadastra um novo adesivo no sistema.
     *
     * @param nome        nome do adesivo
     * @param preco       preço unitário do adesivo
     * @param descricao   descrição do adesivo
     * @param estoque     quantidade em estoque
     * @param vendedor    vendedor responsável pelo adesivo
     * @param tamanho     tamanho do adesivo
     * @return objeto {@link Adesivo} criado
     * @throws QuantidadeInvalidaException se o estoque for negativo
     */
    public Adesivo cadastrarAdesivo(String nome, double preco, String descricao, int estoque, Vendedor vendedor,
            String tamanho)
            throws Exception {

        if (estoque < 0)
            throw new QuantidadeInvalidaException();

        Adesivo a = new Adesivo(contagemId, nome, preco, descricao, estoque, tamanho);
        a.setVendedor(vendedor);
        this.produtos.add(a);
        vendedor.adicionarProduto(a);
        contagemId += 1;
        return a;
    }

    /**
     * Remove um produto do sistema pelo seu ID.
     *
     * @param id identificador do produto a ser removido
     * @throws ProdutoNaoEncontradoException se o produto não for encontrado
     */
    public void removerProduto(int id) throws Exception {
        for (Produto p : produtos) {
            if (p.getId() == id) {
                produtos.remove(p);
                return;
            }
        }
        throw new ProdutoNaoEncontradoException();
    }

    /**
     * Remove um produto do sistema pelo seu nome.
     *
     * @param nome nome do produto a ser removido
     * @throws ProdutoNaoEncontradoException se o produto não for encontrado
     */
    public void removerProduto(String nome) throws Exception {
        for (Produto p : produtos) {
            if (p.getNome() == nome) {
                produtos.remove(p);
                return;
            }
        }
        throw new ProdutoNaoEncontradoException();
    }

    /**
     * Converte um usuário comum em vendedor.
     *
     * @param usuario usuário a ser convertido
     * @param senha   senha para validação
     * @return um novo objeto {@link Vendedor}, ou null se a senha estiver incorreta
     */
    public Vendedor virarVendedor(Usuario usuario, String senha) {
        if (usuario.verificarSenha(senha)) {
            Vendedor vendedor = new Vendedor(usuario.getLogin(), senha, usuario.getSaldo());
            this.usuarios.remove(usuario);
            this.usuarios.add(vendedor);
            return vendedor;
        }
        return null;
    }

    /**
     * Verifica se o login fornecido já existe.
     *
     * @param login login a ser verificado
     * @return 0 se não existe, 1 se já existe entre os usuários, 2 se é o login do sistema
     */
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

    /**
     * Filtra os produtos cadastrados por tipo (classe).
     *
     * @param tipo classe do tipo de produto (ex: Doce.class)
     * @return lista de produtos do tipo especificado
     */
    public List<Produto> filtrarPorTipo(Class<? extends Produto> tipo) {
        ProdutoPorTipoFiltro filtro = new ProdutoPorTipoFiltro(tipo);
        return filtro.meetFilter(this.produtos);
    }

    /**
     * Filtra os produtos cadastrados por uma tag específica.
     *
     * @param descricao descrição da tag
     * @return lista de produtos que possuem a tag
     */
    public List<Produto> filtrarPorTag(String descricao) {
        ProdutoPorTagFiltro filtro = new ProdutoPorTagFiltro(descricao);
        return filtro.meetFilter(this.produtos);
    }

    /**
     * Filtra as vendas realizadas dentro de um intervalo de datas.
     *
     * @param inicio data de início (inclusive)
     * @param fim    data de fim (inclusive)
     * @return lista de itens vendidos dentro do intervalo
     */
    public List<ItemVenda> filtrarPorData(LocalDate inicio, LocalDate fim) {
        ItemVendaPorDataFiltro filtro = new ItemVendaPorDataFiltro(inicio, fim);
        return filtro.meetFilter(this.vendas);
    }

    /**
     * Adiciona uma promoção a um produto.
     *
     * @param p        produto a ser promovido
     * @param unidades quantidade de unidades necessárias para aplicar o desconto
     * @param preco    preço promocional
     */
    public void adicionarPromocao(Produto p, int unidades, double preco) {
        p.setPromocao(new Promocao(unidades, preco));
    }

    /**
     * Remove a promoção de um produto.
     *
     * @param p produto que terá a promoção removida
     */
    public void removerPromocao(Produto p) {
        p.setPromocao(null);
    }


    /**
     * Método de teste que imprime os produtos cadastrados.
     */
    public void imprimirProdutos() {
        System.out.println("Produtos do sistema:");
        for (Produto p : this.produtos) {
            System.out.println(p.getId() + " - " + p.getNome() + " (" + p.getEstoque() + ")");
        }
        System.out.println();
    }
}
