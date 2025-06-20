package laricaco;

/**
 * Interface genérica {@code Filter} que define um critério de filtragem para objetos do tipo {@code T}.
 * 
 * <p>Essa interface é útil para aplicar diferentes estratégias de filtragem em listas ou coleções de objetos,
 * como eventos, clientes, locais, entre outros.</p>
 *
 * @param <T> o tipo de objeto a ser filtrado
 */

public interface Filtro <T>{

    /**
     * Aplica o critério de filtragem ao objeto fornecido.
     *
     * @param item o objeto a ser avaliado
     * @return {@code true} se o objeto satisfaz o critério do filtro, {@code false} caso contrário
     */
    boolean apply(T item);
}
