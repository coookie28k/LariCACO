package laricaco.model;


/**
 * Representa um doce que é um tipo de produto disponível no sistema.
 * Herda os atributos e comportamentos da classe {@link Produto}.
 */
public class Doce extends Produto {

    /**
     * Construtor da classe Doce.
     * 
     * @param id         identificador único do doce
     * @param nome       nome do doce
     * @param preco      preço unitário do doce
     * @param descricao  descrição do doce
     * @param estoque    quantidade disponível em estoque
     */
    Doce(int id, String nome, double preco, String descricao, int estoque) {
        super(id, nome, preco, descricao, estoque);
    }

}
