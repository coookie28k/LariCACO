package laricaco;

import java.time.LocalDate;
import java.util.List;

public class Carrinho {
    private List<ItemVenda> itens;
    private boolean status;

    Carrinho(List<ItemVenda> itens, boolean status) {
        this.itens = itens;
        this.status = status;
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

    public void adicionarItem(Produto prod, int quant) {
        ItemVenda item = new ItemVenda(LocalDate.now(), prod, quant);
        itens.add(item);
    }
}
