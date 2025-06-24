
    package laricaco.model;

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
            caco = Persistencia.carregarSistema("sistema.dat");

            if (caco == null) {
                System.out.println("Sistema não encontrado. Criando novo.");
                caco = SistemaGerenciamento.getInstance(0.1, 1000.0, "admin", "senha123");
            } else {
                SistemaGerenciamento.setInstancia(caco);
            }

            launch(args);
        }

        @Override
        public void stop() {
            Persistencia.salvarSistema(caco, "sistema.dat");
            System.out.println("Sistema salvo ao encerrar o programa.");
        }
    }
