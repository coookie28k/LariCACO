package laricaco.model.Filtros;

import java.util.ArrayList;
import java.util.List;

import laricaco.model.Filtro;
import laricaco.model.Produto;
import laricaco.model.Tag;

public class ProdutoPorTagFiltro implements Filtro<Produto> {

    private final String tag;

    public ProdutoPorTagFiltro(String tag) {
        this.tag = tag;
    }

    @Override
    public List<Produto> meetFilter(List<Produto> lista) {
        List<Produto> produtosFiltrados = new ArrayList<>();
        for (Produto p : lista) {
            for (Tag t : p.getTag())
                if (t.getTag() == tag)
                    produtosFiltrados.add(p);
        }
        return produtosFiltrados;
    }

}
