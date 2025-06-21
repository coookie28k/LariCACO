package laricaco;

import java.time.LocalDate;
import java.util.ArrayList;
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
        if (prod.getEstoque() < quant) {
            throw new EstoqueInsuficienteException();
        } else {
            ItemVenda item = new ItemVenda(LocalDate.now(), prod, quant);
            this.itens.add(item);
        }
    }

    public void removerItem(Produto prod) {
        for (ItemVenda i : this.itens) {
            if (i.getProduto() == prod)
                this.itens.remove(i);
        }
    }

    public double calcularTotal() {
        double total = 0;
        for (ItemVenda i : this.itens) {
            total += i.getTotal();
        }
        return total;
    }
}
