package laricaco.model.Exceptions;

/**
 * Exceção lançada quando se tenta registrar um novo usuário
 * com um login que já está em uso no sistema.
 * Essa verificação impede duplicidade de logins para garantir unicidade.
 */
public class LoginJaExistenteException extends Exception {

    /**
     * Cria uma nova exceção com a mensagem padrão
     * "Este login já está em uso.".
     */
    public LoginJaExistenteException() {
        super("Este login ja esta em uso.");
    }
}
