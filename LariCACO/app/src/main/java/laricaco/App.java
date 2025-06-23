/*
 * Material usado na disciplina MC322 - Programação orientada a objetos.
 */

package laricaco;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    /**
     * Aplicação principal
     * 
     * @param args
     */
    public static SistemaGerenciamento caco;
    public static SistemaUI sistema;

    @Override
    public void start(Stage primaryStage) throws Exception {
        sistema = new SistemaUI(primaryStage);
        sistema.mostrarTela("TelaInicial");
    }

    public static void main(String[] args) throws Exception {

        caco = new SistemaGerenciamento(0.1, 0, "caco@mail.com", "senhacaco");
        Usuario vend = caco.criarUsuario("vend@mail.com", "senha456", 100);
        Vendedor vendedor = caco.virarVendedor(vend, "senha456");

        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 5, "Brigadeiro!", 10, vendedor);
        brigadeiro.adicionarTag("Contem lactose");
        brigadeiro.adicionarTag("Vegetariano");

        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);
        coxinha.adicionarTag("Contem carne");
        coxinha.adicionarTag("Sem lactose");
        caco.adicionarPromocao(coxinha, 2, 2);

        Salgado paoDeQueijo = caco.cadastrarSalgado("pao de queijo", 4, "Pao de queijo!", 10, vendedor);
        paoDeQueijo.adicionarTag("Vegetariano");
        paoDeQueijo.adicionarTag("Contem lactose");

        Adesivo adesivoPeixe = caco.cadastrarAdesivo("adesivo eu odeio o peixe do bandejao", 10,
                "Adesivo com o desenho de um peixe", 20, vendedor, "medio");
        adesivoPeixe.adicionarTag("Adesivo medio");

        Adesivo adesivoWordpress = caco.cadastrarAdesivo("adesivo eu codo em wordpress", 10,
                "Adesivo eu codo em wordpress", 20, vendedor, "pequeno");
        adesivoWordpress.adicionarTag("Adesivo pequeno");

        launch(args);
    }
}
