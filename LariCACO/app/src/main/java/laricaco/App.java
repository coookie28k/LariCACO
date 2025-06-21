/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package laricaco;

import java.time.LocalDate;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    /**
     * Aplicação principal
     * 
     * @param args
     */
    
     public static SistemaUI sistema;
    @Override
    public void start(Stage primaryStage) throws Exception {
        sistema = new SistemaUI(primaryStage);
        sistema.mostrarTela("TelaInicial");
    }

    public static void main(String[] args) throws Exception {

        launch(args); 
        SistemaGerenciamento caco = new SistemaGerenciamento(0.1, 0, "caco@mail.com", "senhacaco");
        Usuario lina = caco.criarUsuario("lina@mail.com", "senha123", 200);
        Usuario vend = caco.criarUsuario("vend@mail.com", "senha456", 100);
        Vendedor vendedor = caco.virarVendedor(vend, "senha456");

        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 5, "Brigadeiro!", 10, vendedor);
        brigadeiro.adicionarTag("Sem lactose");
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

        List<Produto> filtroProd = caco.filtrarPorTipo(Salgado.class);
        System.out.println("Filtro de salgados");
        for (Produto prod : filtroProd)
            System.out.println(prod.getId() + " - " + prod.getNome() + " (" + prod.getEstoque() + ")");

        System.out.println();

        List<ItemVenda> filtroItem = caco.filtrarPorData(LocalDate.of(2025, 4, 1), LocalDate.of(2025, 6, 28));
        System.out.println("Filtro de data");
        for (ItemVenda it : filtroItem)
            System.out.println(it.getProduto().getNome() + " - " + it.getDataFormatada());

        System.out.println();
        List<Produto> filtroTag = caco.filtrarPorTag("Sem lactose");
        System.out.println("Filtro sem lactose");
        for (Produto prod : filtroTag)
            System.out.println(prod.getId() + " - " + prod.getNome() + " (" + prod.getEstoque() + ")");
    }
}
