/*
 * Local.java
 * 
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */
package laricaco;

/**
 * Contém a estrutura de implementação de um Local.
 * 
 * @author Gabriel Leite - 216180
 */
public class Local {
    private String nome;
    private double capacidadeMaxima;

    /**
     * Construtor da classe Local
     * 
     * @param nome o nome do local
     */
    public Local(String nome, double capacidadeMaxima) {
        this.nome = nome;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    /**
     * Retorna o nome do evento
     * 
     * @return o nome do evento
     */
    public String getNome() {
        return nome;
    }

    /**
     * Altera o nome do evento para `nome`
     * 
     * @param nome o novo nome do evento
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Retorna a capacidade do local
     * 
     * @return a capacidade do local
     */
    public double getCapacidade() {
        return capacidadeMaxima;
    }

    /**
     * Altera a capacidade máxima do local para `capacidadeMaxima`
     * 
     * @param capacidadeMaxima a nova capacidade máxima do local
     */
    public void setCapacidade(double capacidadeMaxima) {
        this.capacidadeMaxima = capacidadeMaxima;
    }
}
