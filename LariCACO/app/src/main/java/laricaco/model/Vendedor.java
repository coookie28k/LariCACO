package laricaco.model;

import java.util.ArrayList;
import java.util.List;


/**
 * Representa um vendedor no sistema, que é um tipo de usuário com
 * uma lista de vendas realizadas e produtos cadastrados.
 */
public class Vendedor extends Usuario {
    
    /** Lista de vendas realizadas pelo vendedor. */
    private List<ItemVenda> minhasVendas;

    /** Lista de produtos cadastrados pelo vendedor. */
    private List<Produto> meusProdutos;

    /**
     * Constrói um novo vendedor com login, senha e saldo inicial.
     * Inicializa as listas de vendas e produtos como vazias.
     * 
     * @param login login do vendedor
     * @param senha senha do vendedor
     * @param saldo saldo inicial do vendedor
     */
    public Vendedor(String login, String senha, double saldo) {
        super(login, senha, saldo);
        this.minhasVendas = new ArrayList<>();
        this.meusProdutos = new ArrayList<>();
    }

    /**
     * Retorna a lista de vendas realizadas pelo vendedor.
     * 
     * @return lista de objetos ItemVenda
     */ 
    public List<ItemVenda> getMinhasVendas() {
        return minhasVendas;
    }

    /**
     * Define a lista de vendas realizadas pelo vendedor.
     * 
     * @param minhasVendas nova lista de vendas
     */
    public void setMinhasVendas(List<ItemVenda> minhasVendas) {
        this.minhasVendas = minhasVendas;
    }

    /**
     * Retorna a lista de produtos cadastrados pelo vendedor.
     * 
     * @return lista de produtos
     */
    public List<Produto> getMeusProdutos() {
        return meusProdutos;
    }

    /**
     * Define a lista de produtos cadastrados pelo vendedor.
     * 
     * @param meusProdutos nova lista de produtos
     */
    public void setMeusProdutos(List<Produto> meusProdutos) {
        this.meusProdutos = meusProdutos;
    }

    /**
     * Adiciona um produto à lista de produtos do vendedor.
     * 
     * @param produto produto a ser adicionado
     */
    public void adicionarProduto(Produto produto) {
        this.meusProdutos.add(produto);
    }

    /**
     * Remove um produto da lista de produtos do vendedor.
     * 
     * @param produto produto a ser removido
     */
    public void removerProduto(Produto produto) {
        this.meusProdutos.remove(produto);
    }

    /**
     * Adiciona uma venda à lista de vendas realizadas pelo vendedor.
     * 
     * @param itemVenda venda a ser adicionada
     */
    public void adicionarItemVenda(ItemVenda itemVenda) {
        this.minhasVendas.add(itemVenda);
    }

     /**
     * Método de teste que imprime as vendas realizadas pelo vendedor,
     * incluindo o nome do produto, quantidade vendida e data da venda.
     */
    public void imprimirVendas() {
        System.out.println("Vendas de " + this.getLogin() + ":");
        for (ItemVenda i : this.minhasVendas) {
            System.out.println(i.getProduto().getNome() + " (" + i.getQuantidade() + ") - " + i.getDataFormatada());
        }
        System.out.println();
    }

    /**
     * Método de teste que imprime os produtos cadastrados pelo vendedor,
     * mostrando nome e quantidade em estoque.
     */
    public void imprimirProdutos() {
        System.out.println("Produtos de " + this.getLogin() + ":");
        for (Produto i : this.meusProdutos) {
            System.out.println(i.getNome() + " (" + i.getEstoque() + ")");
        }
        System.out.println();
    }
}
