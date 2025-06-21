package laricaco;

public class Usuario {
    private String login;
    private String senha;
    private double saldo;
    private Carrinho carrinho;

    Usuario(String login, String senha, double saldo, Carrinho carrinho) {
        this.login = login;
        this.senha = senha;
        this.saldo = saldo;
        this.carrinho = carrinho;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean verificaSenha(String senha) {
        if (senha == this.senha)
            return true;
        else
            return false;
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

    public void adicionaSaldo(double valor) {
        this.saldo += valor;
    }

    public void retiraSaldo(double valor) {
        this.saldo -= valor;
    }
}
