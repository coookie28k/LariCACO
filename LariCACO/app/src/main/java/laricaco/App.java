/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package laricaco;

public class App {

    /**
     * Aplicação principal
     * 
     * @param args
     */
    public static void main(String[] args) throws Exception {

        SistemaGerenciamento caco = new SistemaGerenciamento(0.1, 0, "caco@mail.com", "senhacaco");
        Usuario lina = caco.criarUsuario("lina@mail.com", "senha123", 200);
        Usuario vend = caco.criarUsuario("vend@mail.com", "senha456", 100);
        Vendedor vendedor = caco.virarVendedor(vend, "senha456");

        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 5, "Brigadeiro!", 10, vendedor);
        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);
        lina.getCarrinho().adicionarItem(brigadeiro, 2);

        System.out.println("Saldo de lina: " + lina.getSaldo());
        System.out.println("Saldo de vendedor: " + vendedor.getSaldo());
        System.out.println("Saldo de caco: " + caco.getSaldo());
        caco.realizarVenda(lina, vendedor);

        System.out.println("\nSaldo de lina: " + lina.getSaldo());
        System.out.println("Saldo de vendedor: " + vendedor.getSaldo());
        System.out.println("Saldo de caco: " + caco.getSaldo() + "\n");

        vendedor.imprimirVendas();
        vendedor.imprimirProdutos();
        caco.imprimirProdutos();
    }
}
