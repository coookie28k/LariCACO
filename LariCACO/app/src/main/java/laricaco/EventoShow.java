/*

 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package laricaco;

public class EventoShow extends Evento {
        
    private String artista;
    
    /**
    * Construtor da classe EventocShow
    * @param nome o nome do Evento
    * @param local o local do Evento
    * @param precoIngresso o preço do Ingresso do Evento
    * @param artista o artista do Evento
    * @param organizadora a organizadora do Evento
    */
    public EventoShow(String nome, Local local, double precoIngresso, Organizadora organizadora, String data, String artista) {
        super(nome, local, precoIngresso, organizadora, data);
        this.artista = artista;

    }

    /**
    * Retorna a descrição do Evento
    * @return a descrição do Evento
    */
    public String getDescricao() {
        return "Show: " + this.nome + " - Artista: " + this.artista  + " - Local: " + this.local + " - Data: " + this.data;
    }
    
    /**
    * Retorna o preço do Ingresso do Evento
    * @return o preço do Ingresso do Evento
    */
    public double getPrecoIngresso() {
        return this.precoIngresso;
    }
}
