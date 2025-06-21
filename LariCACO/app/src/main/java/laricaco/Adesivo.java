package laricaco;

public class Adesivo extends Produto {
    private String tamanho;

    public Adesivo(int id, String nome, double preco, String descricao, int estoque,
            String tamanho) {
        super(id, nome, preco, descricao, estoque);
        this.tamanho = tamanho;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
}
