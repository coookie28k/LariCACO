package laricaco.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laricaco.model.Exceptions.EstoqueInsuficienteException;

import java.io.Serializable;

/**
 * Representa um carrinho de compras que armazena itens de venda.
 * Permite adicionar, remover itens e calcular o total da compra.
 */
public class Carrinho implements Serializable{
    private static final long serialVersionUID = 1L;

    private List<ItemVenda> itens;
    private boolean status;

    /**
     * Construtor padrão que inicializa o carrinho vazio e com status aberto.
     */
    Carrinho() {
        this.itens = new ArrayList<>();
        this.status = false;
    }

    /**
     * Retorna a lista de itens atualmente no carrinho.
     * 
     * @return lista de itens do carrinho
     */
    public List<ItemVenda> getItens() {
        return itens;
    }

    /**
     * Define a lista de itens do carrinho.
     * 
     * @param itens nova lista de itens
     */
    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    /**
     * Verifica o status do carrinho.
     * 
     * @return true se o carrinho estiver fechado, false se estiver aberto
     */
    public boolean isStatus() {
        return status;
    }

     /**
     * Define o status do carrinho.
     * 
     * @param status novo status do carrinho (true para fechado, false para aberto)
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Adiciona um item ao carrinho com a quantidade especificada.
     * Verifica se há estoque suficiente antes de adicionar.
     * 
     * @param prod produto a ser adicionado
     * @param quant quantidade do produto
     * @throws EstoqueInsuficienteException se não houver estoque suficiente
     * @throws Exception para outros erros gerais
     */
    public void adicionarItem(Produto prod, int quant) throws Exception {
        if (prod.getEstoque() - prod.getNoCarrinho() < quant) {
            throw new EstoqueInsuficienteException();
        } else {
            ItemVenda item = new ItemVenda(LocalDate.now(), prod, quant);
            prod.adicionarQuantidadeNoCarrinho(quant);
            System.out.println("No carrinho: " + prod.getNoCarrinho() + ". Em estoque : " + prod.getEstoque());
            this.itens.add(item);
        }
    }

    /**
     * Remove um produto do carrinho, atualizando a quantidade no produto.
     * 
     * @param prod produto a ser removido do carrinho
     */
    public void removerItem(Produto prod) {
        Iterator<ItemVenda> it = this.itens.iterator();
        while (it.hasNext()) {
            ItemVenda i = it.next();
            if (i.getProduto() == prod) {
                prod.retirarQuantidadeDoCarrinho(i.getQuantidade());
                it.remove();
                break;
            }
        }
    }

    /**
     * Calcula o valor total dos itens presentes no carrinho.
     * 
     * @return soma total dos preços multiplicados pelas quantidades dos itens
     */
    public double calcularTotal() {
        double total = 0;
        for (ItemVenda i : this.itens) {
            total += i.getTotal();
        }
        return total;
    }

    /**
     * Remove todos os itens do carrinho e redefine o status.
     */
    public void limpar() {
        this.itens.clear(); // esvazia a lista
        this.status = false; // marca como “em aberto” novamente
    }

}
