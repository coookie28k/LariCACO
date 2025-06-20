package laricaco;

public class Promocao {
    private int unidades;
    private double preco;

    public Promocao(int unidades, double preco){
        this.unidades = unidades;
        this.preco = preco;
    }

    public int getUnidades() {
        return unidades;
    }

    public void setUnidades(int unidades) {
        this.unidades = unidades;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
