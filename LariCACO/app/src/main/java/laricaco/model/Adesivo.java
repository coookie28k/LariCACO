package laricaco.model;

/**
 * Representa um adesivo que é um tipo de produto disponível no sistema.
 * Herda os atributos e comportamentos da classe {@link Produto}.
 */
public class Adesivo extends Produto {
    private String tamanho;

    /**
     * Construtor da classe adesivo
     * 
     * @param id        id do produto
     * @param nome      nome do produto
     * @param preco     preco do produto
     * @param descricao descricao do produto
     * @param estoque   quantidade em estoque
     * @param tamanho   tamanho do adesivo
     */
    public Adesivo(int id, String nome, double preco, String descricao, int estoque,
            String tamanho) {
        super(id, nome, preco, descricao, estoque);
        this.tamanho = tamanho;
    }

    /**
     * Retorna o tamanho do adesivo
     * 
     * @return tamanho do adesivo
     */
    public String getTamanho() {
        return tamanho;
    }

    /**
     * Altera o tamanho do adesivo para tamanho
     * 
     * @param tamanho novo tamanho do adesivo
     */
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
