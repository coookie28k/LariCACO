package laricaco;

import java.util.List;

public interface Filtro<T> {

    public List<T> meetFilter(List<T> lista);

}
