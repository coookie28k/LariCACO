package laricaco;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laricaco.Exceptions.EstoqueInsuficienteException;

public class Carrinho {
    private List<ItemVenda> itens;
    private boolean status;

    Carrinho() {
        this.itens = new ArrayList<>();
        this.status = false;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

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
