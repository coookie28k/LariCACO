package laricaco.model.Exceptions;

/**
 * Exceção lançada quando um produto solicitado não é encontrado
 * no sistema, seja por nome, ID ou outro critério de busca.
 */
public class ProdutoNaoEncontradoException extends Exception {

    /**
     * Cria uma nova exceção com a mensagem padrão
     * "Produto não encontrado.".
     */
    public ProdutoNaoEncontradoException() {
        super("Produto nao encontrado.");
    }
}
