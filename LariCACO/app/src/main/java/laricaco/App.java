
    package laricaco;

    import javafx.application.Application;
    import javafx.stage.Stage;

    
    /**
     * Classe principal da aplicação Laricaco.
     * Extende Application para iniciar a interface gráfica JavaFX.
     * Foi utilizado IA nesse projeto para tirar dúvidas sobre classes, sobre Java, para auxiliar no javadoc e para auxiliar na composição das telas e controllers.
     */
    public class App extends Application {

        /**
         * Instância do sistema de gerenciamento.
         */
        public static SistemaGerenciamento caco;

        /**
         * Interface do sistema que gerencia as telas.
         */
        public static SistemaUI sistema;

        /**
         * Método principal que inicia a aplicação JavaFX.
         * Inicializa o sistema de interface e mostra a tela inicial.
         * 
         * @param primaryStage palco principal da aplicação JavaFX
         * @throws Exception caso ocorra algum erro na inicialização
         */
        @Override
        public void start(Stage primaryStage) throws Exception {
            sistema = new SistemaUI(primaryStage);
            sistema.mostrarTela("TelaInicial");
        }

        /**
         * Método main que inicia a aplicação.
         * Cria usuários, vendedores e produtos para demonstração.
         * Por fim, lança a aplicação JavaFX.
         * 
         * @param args argumentos de linha de comando
         * @throws Exception caso ocorra algum erro na execução
         */
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
