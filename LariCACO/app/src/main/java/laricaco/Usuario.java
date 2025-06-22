package laricaco;

public class Usuario {
    private String login;
    private String senha;
    private double saldo;
    private Carrinho carrinho;

    Usuario(String login, String senha, double saldo) {
        this.login = login;
        this.senha = senha;
        this.saldo = saldo;
        this.carrinho = new Carrinho();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean verificarSenha(String senhaInput) {
        return this.senha.equals(senhaInput);
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public void adicionarSaldo(double valor) {
        this.saldo += valor;
    }

    public void retirarSaldo(double valor) {
        this.saldo -= valor;
    }
}
