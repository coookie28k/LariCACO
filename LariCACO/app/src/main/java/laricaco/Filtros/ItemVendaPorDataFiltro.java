package laricaco.Filtros;

import java.time.LocalDate;
import java.util.Objects;

import laricaco.Filtro;
import laricaco.ItemVenda;

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
        Objects.requireNonNull(inicio, "início não pode ser nulo");
        Objects.requireNonNull(fim, "fim não pode ser nulo");
        if (inicio.isAfter(fim))
            throw new IllegalArgumentException("Data inicial deve ser antes ou igual à final");

        this.inicio = inicio;
        this.fim = fim;
    }

    @Override
    public boolean apply(ItemVenda venda) {
        if (venda == null)
            return false;
        LocalDate d = venda.getData();
        return (d.isEqual(inicio) || d.isAfter(inicio))
                && (d.isEqual(fim) || d.isBefore(fim));
    }
}
