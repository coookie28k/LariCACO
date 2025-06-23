package laricaco;
import java.io.Serializable;

/**
 * Representa um usuário do sistema, com login, senha, saldo e um carrinho de compras.
 */
public class Usuario implements Serializable{

    /** Login do usuário. */
    private String login;
    
    /** Senha do usuário. */
    private String senha;
    
    /** Saldo disponível do usuário. */
    private double saldo;
    
    /** Carrinho de compras associado ao usuário. */
    private Carrinho carrinho;

    /**
     * Constrói um novo usuário com login, senha e saldo inicial.
     * O carrinho é criado vazio.
     * 
     * @param login login do usuário
     * @param senha senha do usuário
     * @param saldo saldo inicial do usuário
     */
    public Usuario(String login, String senha, double saldo) {
        this.login = login;
        this.senha = senha;
        this.saldo = saldo;
        this.carrinho = new Carrinho();
    }

    /**
     * Retorna o login do usuário.
     * 
     * @return login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * Atualiza o login do usuário.
     * 
     * @param login novo login do usuário
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Verifica se a senha fornecida é igual à senha do usuário.
     * 
     * @param senhaInput senha para verificação
     * @return true se a senha for igual; false caso contrário
     */
    public boolean verificarSenha(String senhaInput) {
        return this.senha.equals(senhaInput);
    }

    /**
     * Atualiza a senha do usuário.
     * 
     * @param senha nova senha
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Retorna o saldo disponível do usuário.
     * 
     * @return saldo do usuário
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Atualiza o saldo do usuário.
     * 
     * @param saldo novo saldo do usuário
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    /**
     * Retorna o carrinho de compras do usuário.
     * 
     * @return carrinho associado ao usuário
     */
    public Carrinho getCarrinho() {
        return carrinho;
    }

    /**
     * Atualiza o carrinho de compras do usuário.
     * 
     * @param carrinho novo carrinho
     */
    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    /**
     * Adiciona uma quantidade de um produto ao carrinho do usuário.
     * 
     * @param p produto a ser adicionado
     * @param quant quantidade do produto
     * @throws Exception se a adição falhar (exemplo: quantidade inválida)
     */
    public void adicionarNoCarrinho(Produto p, int quant) throws Exception {
        this.carrinho.adicionarItem(p, quant);
    }

    /**
     * Adiciona valor ao saldo do usuário.
     * 
     * @param valor valor a adicionar
     */
    public void adicionarSaldo(double valor) {
        this.saldo += valor;
    }

    /**
     * Retira valor do saldo do usuário.
     * 
     * @param valor valor a retirar
     */
    public void retirarSaldo(double valor) {
        this.saldo -= valor;
    }
}
