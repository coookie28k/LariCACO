package laricaco;

/**
 * Representa um salgado que é um tipo de produto disponível no sistema.
 * Herda os atributos e comportamentos da classe {@link Produto}.
 */
public class Salgado extends Produto {

     /**
     * Construtor da classe Salgado.
     * 
     * @param id         identificador único do salgado
     * @param nome       nome do salgado
     * @param preco      preço unitário do salgado
     * @param descricao  descrição do salgado
     * @param estoque    quantidade disponível em estoque
     */
    Salgado(int id, String nome, double preco, String descricao, int estoque) {
        super(id, nome, preco, descricao, estoque);
    }

}
