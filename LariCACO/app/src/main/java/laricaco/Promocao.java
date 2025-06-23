package laricaco;

/**
 * Representa uma promoção aplicada a um produto, oferecendo um preço especial
 * para uma determinada quantidade de unidades.
 */
public class Promocao {

    /** Quantidade de unidades necessárias para ativar a promoção. */
    private int unidades;

    /** Preço promocional para a quantidade especificada. */
    private double preco;

    /**
     * Construtor da classe Promocao.
     *
     * @param unidades número de unidades exigidas para aplicar o preço promocional
     * @param preco    valor promocional a ser aplicado
     */
    public Promocao(int unidades, double preco){
        this.unidades = unidades;
        this.preco = preco;
    }

    /**
     * Retorna o número de unidades necessárias para a promoção ser válida.
     *
     * @return número de unidades
     */
    public int getUnidades() {
        return unidades;
    }

    /**
     * Define o número de unidades para ativar a promoção.
     *
     * @param unidades nova quantidade de unidades
     */
    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    /**
     * Retorna o preço promocional definido.
     *
     * @return preço promocional
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Define um novo preço promocional.
     *
     * @param preco novo valor promocional
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }
}
