package laricaco.model.Filtros;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import laricaco.model.Filtro;
import laricaco.model.ItemVenda;

/**
 * Filtro que aceita apenas itens de venda cuja data esteja dentro de um
 * intervalo [início, fim].
 */

public class ItemVendaPorDataFiltro implements Filtro<ItemVenda> {

    private final LocalDate inicio; // inclusive
    private final LocalDate fim; // inclusive

    /**
     * @param inicio data inicial (inclusive)
     * @param fim    data final (inclusive)
     * @throws IllegalArgumentException se {@code inicio} for depois de {@code fim}
     */
    public ItemVendaPorDataFiltro(LocalDate inicio, LocalDate fim) {
        Objects.requireNonNull(inicio, "Inicio nao pode ser nulo");
        Objects.requireNonNull(fim, "Fim nao pode ser nulo");
        if (inicio.isAfter(fim))
            throw new IllegalArgumentException("Data inicial deve ser antes ou igual a final");

        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public List<ItemVenda> meetFilter(List<ItemVenda> lista) {
        List<ItemVenda> vendasNaData = new ArrayList<>();
        for (ItemVenda i : lista) {
            LocalDate d = i.getData();
            if ((d.isEqual(this.inicio) || d.isAfter(this.inicio)) && (d.isEqual(this.fim) || d.isBefore(this.fim)))
                vendasNaData.add(i);
        }

        return vendasNaData;
    }
}
