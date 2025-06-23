package laricaco.Exceptions;

/**
 * Exceção lançada quando uma operação financeira não pode ser concluída
 * devido à insuficiência de saldo disponível.
 */
public class SaldoInsuficienteException extends Exception {

    /**
     * Cria uma nova exceção com a mensagem padrão
     * "Saldo insuficiente.".
     */
    public SaldoInsuficienteException() {
        super("Saldo insuficiente.");
    }
}
