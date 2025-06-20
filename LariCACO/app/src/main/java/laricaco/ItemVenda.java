package laricaco;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ItemVenda {
    
    private static final DateTimeFormatter BR_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDate dataVenda;
    
    
    
    public ItemVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    
    /** Data da venda como {@link LocalDate}. */
    public LocalDate getDataVenda() { return dataVenda; }

    /** Data da venda já formatada em “dd/MM/yyyy”. */
    public String getDataVendaFormatada() {
        return dataVenda.format(BR_FORMAT);
    }
}
