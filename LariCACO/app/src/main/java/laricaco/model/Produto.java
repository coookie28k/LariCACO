package laricaco.model;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

/**
 * Representa um produto genérico disponível para venda.
 * Pode conter promoções, ser associado a um vendedor e possuir tags descritivas.
 */
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único do produto. */
    private final int id;

    /** Nome do produto. */
    private String nome;

    /** Preço unitário do produto. */
    private double preco;

    /** Descrição textual do produto. */
    private String descricao;

    /** Quantidade disponível no estoque. */
    private int estoque;

    /** Quantidade do produto que está atualmente em carrinhos de compra. */
    private int noCarrinho;

    /** Vendedor responsável pelo produto. */
    private Vendedor vendedor;

    /** Lista de tags associadas ao produto. */
    private List<Tag> tagProduto;

    /** Promoção ativa, se houver, associada ao produto. */
    private Promocao promocao;

    /**
     * Construtor da classe Produto.
     *
     * @param id         identificador do produto
     * @param nome       nome do produto
     * @param preco      preço do produto
     * @param descricao  descrição do produto
     * @param estoque    quantidade inicial em estoque
     */
    public Produto(int id, String nome, double preco, String descricao, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.estoque = estoque;

        this.vendedor = null;
        this.tagProduto = new ArrayList<>();
        this.promocao = null;
        this.noCarrinho = 0;
    }

    /**
     * Retorna o identificador do produto.
     *
     * @return id do produto
     */
    public int getId() {
        return id;
    }

    
    /**
     * Retorna a lista de tags associadas ao produto.
     *
     * @return lista de tags
     */
    public List<Tag> getTag() {
        return tagProduto;
    }

    
    /**
     * Define uma nova lista de tags para o produto.
     *
     * @param tags lista de tags
     */
    public void setTag(List<Tag> tags) {
        this.tagProduto = tags;
    }

     /**
     * Adiciona uma tag ao produto.
     *
     * @param tag objeto do tipo Tag
     */
    public void adicionarTag(Tag tag) {
        this.tagProduto.add(tag);
    }

    /**
     * Cria e adiciona uma nova tag com base na descrição.
     *
     * @param descricao descrição da nova tag
     */
    public void adicionarTag(String descricao) {
        Tag tag = new Tag(descricao);
        this.tagProduto.add(tag);
    }

    /**
     * Remove uma tag do produto.
     *
     * @param tag objeto do tipo Tag a ser removido
     */
    public void removerTag(Tag tag) {
        this.tagProduto.remove(tag);
    }

    /**
     * Remove uma tag com a descrição correspondente.
     *
     * @param descricao descrição da tag a ser removida
     */
    public void removerTag(String descricao) {
        Tag tag = null;
        for (Tag t : this.tagProduto)
            if (t.getTag() == descricao)
                tag = t;
        if (tag != null)
            this.tagProduto.remove(tag);
    }

     /**
     * Retorna o preço do produto.
     *
     * @return preço
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Define o preço do produto.
     *
     * @param preco novo preço
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do produto.
     *
     * @param nome novo nome
     */

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a descrição do produto.
     *
     * @return descrição
     */
    public String getDescricao() {
        return descricao;
    }

     /**
     * Define a descrição do produto.
     *
     * @param descricao nova descrição
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a quantidade em estoque.
     *
     * @return estoque
     */
    public int getEstoque() {
        return estoque;
    }

    /**
     * Define a quantidade em estoque.
     *
     * @param estoque novo valor de estoque
     */
    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    /**
     * Retorna o vendedor associado ao produto.
     *
     * @return vendedor
     */
    public Vendedor getVendedor() {
        return vendedor;
    }

     /**
     * Define o vendedor do produto.
     *
     * @param vendedor novo vendedor
     */
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

     /**
     * Reduz a quantidade em estoque.
     *
     * @param quantidade quantidade a ser retirada
     */
    public void retirarEstoque(int quantidade) {
        this.estoque -= quantidade;
    }

    /**
     * Aumenta a quantidade em estoque.
     *
     * @param quantidade quantidade a ser adicionada
     */
    public void adicionarEstoque(int quantidade) {
        this.estoque += quantidade;
    }

    /**
     * Retorna a promoção associada ao produto.
     *
     * @return promoção, ou null se não houver
     */
    public Promocao getPromocao() {
        return promocao;
    }

    /**
     * Define uma promoção para o produto.
     *
     * @param promocao nova promoção
     */
    public void setPromocao(Promocao promocao) {
        this.promocao = promocao;
    }

    /**
     * Retorna a quantidade do produto que está em carrinhos.
     *
     * @return quantidade em carrinhos
     */
    public int getNoCarrinho() {
        return noCarrinho;
    }

    /**
     * Adiciona uma quantidade ao contador de carrinhos.
     *
     * @param quant quantidade a ser adicionada
     */
    public void adicionarQuantidadeNoCarrinho(int quant) {
        this.noCarrinho += quant;
    }

    /**
     * Remove uma quantidade do contador de carrinhos.
     *
     * @param quant quantidade a ser removida
     */
    public void retirarQuantidadeDoCarrinho(int quant) {
        this.noCarrinho -= quant;
    }
}
