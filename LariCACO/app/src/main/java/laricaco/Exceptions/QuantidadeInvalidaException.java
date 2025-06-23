package laricaco.Exceptions;

/**
 * Exceção lançada quando uma quantidade inválida é informada,
 * como valores negativos ou zero em operações que exigem quantidades positivas.
 */
public class QuantidadeInvalidaException extends Exception {

    /**
     * Cria uma nova exceção com a mensagem padrão
     * "Quantidade inválida.".
     */
    public QuantidadeInvalidaException() {
        super("Quantidade invalida.");
    }
}
