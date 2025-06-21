package laricaco.Filtros;

import java.util.ArrayList;
import java.util.List;

import laricaco.Filtro;
import laricaco.Produto;

/**
 * Filtro que aceita apenas instâncias de uma subclasse específica de Produto.
 */

public class ProdutoPorTipoFiltro implements Filtro<Produto> {
    private final Class<? extends Produto> classeSelecionada;

    /**
     * @param classeSelecionada subclasse de Produto que deve passar no filtro
     */
    public ProdutoPorTipoFiltro(Class<? extends Produto> classeSelecionada) {
        this.classeSelecionada = classeSelecionada;
    }

    @Override
    public List<Produto> meetFilter(List<Produto> produtos) {
        List<Produto> produtosDoTipo = new ArrayList<>();
        for (Produto p : produtos) {
            if (this.classeSelecionada.isInstance(p))
                produtosDoTipo.add(p);
        }

        return produtosDoTipo;
    }
}
