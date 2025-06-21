package laricaco;

public class Produto {
    private int id;
    private String nome;
    private double preco;
    private String descricao;
    private int estoque;
    private Vendedor vendedor;
    private Tag tagProduto;

    // falta: promocao, tags

    Produto(int id, String nome, double preco, String descricao, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.estoque = estoque;
        this.vendedor = null;
        this.tagProduto = null;
    }

    public String getTag() {
        return tagProduto.getTag();
    }

    public void setTag(Tag tagnova) {
        this.tagProduto = tagnova;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
