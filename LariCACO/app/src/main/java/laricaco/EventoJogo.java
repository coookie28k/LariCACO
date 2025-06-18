/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */


package laricaco;

import java.util.List;

public class EventoJogo extends Evento {
        
    private List<String> times;
    /**
    * Construtor da classe EventoEsporte
    * @param nome o nome do Evento
    * @param local o local do Evento
    * @param precoIngresso o preço do Ingresso do Evento
    */
    public EventoJogo(String nome, Local local, double precoIngresso, Organizadora organizadora, String data, List<String> times) {
        super(nome, local, precoIngresso, organizadora, data);
        this.times = times;
    }

    /**
     * Retorna a lista com os nomes dos times que se enfrentam no Evento
     * @return a lista com os nomes dos times do Evento
     */
    public List<String> getTimes() {
        return times;
    }

    /**
     * Retorna uma string contendo a descri o do Evento, com seu nome, times, local e data
     * @return uma string com a descri o do Evento
     */
    @Override
    public String descricao() {
        return "Esporte: " + this.nome + " - Times: " + this.times + " - Local: " + this.local;
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
