/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package laricaco;

import java.util.List;

public class EventoFestival extends Evento {
        
    private List<String> lineup;
    private int duracao;
    
    /**
    * Construtor da classe EventoFestival
    * @param nome o nome do Evento
    * @param local o local do Evento
    * @param precoIngresso o preço do Ingresso do Evento
    * @param dataInicio a data de início do Festival
    * @param dataFim a data de fim do Festival
    */
    public EventoFestival(String nome, Local local, double precoIngresso, Organizadora organizadora, String data, List<String> lineup, int duracao) {
        super(nome, local, precoIngresso, organizadora, data);
        this.lineup = lineup;
        this.duracao = duracao;
    }

    /**
    * Retorna a lista com os nomes dos artistas que se apresentarão no Festival
    * @return a lista com os nomes dos artistas do Festival
    */
    public List<String> getLineup() {
        return this.lineup;
    }
    
    /**
    * Retorna a dura o do Festival em dias
    * @return a dura o do Festival
    */
    public int getDuracao() {
        return this.duracao;
    }


    /**
     * Retorna uma string contendo a descri o do Festival, com seu nome, lineup, local e dura o
     * @return uma string com a descri o do Festival
     */
    public String descricao() {
        return "Festival: " + this.nome + " - Lineup: " + this.lineup + " - Local: " + this.local + " - Duração: " + this.duracao;
    }

    /**
    * Retorna o preço do Ingresso do Evento
    * @return o preço do Ingresso do Evento
    */
    @Override
    public double getPrecoIngresso() {
        return this.precoIngresso;
    }
}
