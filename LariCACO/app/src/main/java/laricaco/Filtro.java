package laricaco;

import java.util.List;

/**
 * Interface genérica que define um filtro aplicável a listas de objetos.
 * 
 * @param <T> o tipo de objeto que será filtrado
 */
public interface Filtro<T> {

    /**
     * Aplica um critério de filtragem sobre uma lista de objetos.
     * 
     * @param lista lista de objetos a serem filtrados
     * @return uma nova lista contendo apenas os objetos que atendem ao critério do filtro
     */
    public List<T> meetFilter(List<T> lista);

}
