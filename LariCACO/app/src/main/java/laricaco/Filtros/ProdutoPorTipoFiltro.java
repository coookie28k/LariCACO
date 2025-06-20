package laricaco.Filtros;
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
    public boolean apply(Produto produto) {
        return classeSelecionada.isInstance(produto);
    }
}
