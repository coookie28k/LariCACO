package laricaco.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import laricaco.App;

public class MenuUsuarioController {

    @FXML
    private void onVoltar() {
        // Desliga o usuario
        App.sistema.setLogado(null);
        try {
            App.sistema.mostrarTela("TelaInicial");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao voltar para a tela inicial.");
        }
    }

    @FXML
    private void onComprar() {
        try {
            App.sistema.mostrarTela("Comprar");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao abrir tela de comprar");
        }

    }

    @FXML
    private void onVender() {
        try {
            App.sistema.mostrarTela("TelaVender");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao abrir tela de vender");
        }
    }

    @FXML
    private void onMeusDados() {
        try {
            App.sistema.mostrarTela("MeusDados");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao abrir tela de dados do usuário");
        }
    }

    @FXML
    private void onDeslogar() {
        // Desliga o usuario
        App.sistema.setLogado(null);
        try {
            App.sistema.mostrarTela("TelaInicial");
            mostrarAlerta("Logout", "Você foi deslogado com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao deslogar.");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
