package laricaco.Exceptions;

public class LoginJaExistenteException extends Exception {
    public LoginJaExistenteException() {
        super("Este login ja esta em uso.");
    }
}
