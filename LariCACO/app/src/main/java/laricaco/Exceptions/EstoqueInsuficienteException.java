package laricaco.Exceptions;


/**
 * Exceção lançada quando uma operação requer mais unidades de um produto
 * do que estão disponíveis em estoque.
 */
public class EstoqueInsuficienteException extends Exception {

    /**
     * Cria uma nova exceção com a mensagem padrão 
     * "Estoque insuficiente.".
     */
    public EstoqueInsuficienteException() {
        super("Estoque insuficiente.");
    }
}
