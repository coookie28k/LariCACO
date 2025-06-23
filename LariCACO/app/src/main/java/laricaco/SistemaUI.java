package laricaco;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Classe que representa o sistema UI de puxar telas.
 */
public class SistemaUI {

    /** Stage principal da aplicação (janela). */
    private Stage stage;

    /** Cena atual exibida na janela. */
    private Scene scene;

    /** Cliente atualmente logado no sistema. */
    private Usuario logado;

    /** Largura padrão da janela */
    private static final double LARGURA_PADRAO = 800;

    /** Altura padrão da janela */
    private static final double ALTURA_PADRAO = 600;

    /**
     * Construtor que recebe o Stage principal da aplicação.
     * @param stage Stage principal
     */
    public SistemaUI(Stage stage) {
        this.stage = stage;
    }

    /**
     * Exibe a tela a partir do nome do arquivo FXML (sem extensão).
     * Se for a primeira vez, cria a Scene, caso contrário, só troca o root.
     * 
     * @param nomeFXML nome do arquivo FXML, ex: "MainWindow", "Eventos"
     * @throws IOException se não encontrar ou erro ao carregar o FXML
     */
    public void mostrarTela(String nomeFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + nomeFXML + ".fxml"));
        Parent root = loader.load();

        if (scene == null) {
            scene = new Scene(root, LARGURA_PADRAO, ALTURA_PADRAO);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
            stage.setWidth(LARGURA_PADRAO);
            stage.setHeight(ALTURA_PADRAO);
        }
        stage.show();
    }

    /**
     * Retorna o Stage principal da aplicação.
     * 
     * @return Stage principal
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Retorna o usuário atualmente logado no sistema.
     * 
     * @return objeto {@link Usuario} logado, ou null se ninguém estiver logado
     */
    public Usuario getLogado() {
        return logado;
    }

    /**
     * Define o usuário atualmente logado no sistema.
     * 
     * @param logado objeto {@link Usuario} que será definido como logado
     */
    public void setLogado(Usuario logado) {
        this.logado = logado;
    }
}
