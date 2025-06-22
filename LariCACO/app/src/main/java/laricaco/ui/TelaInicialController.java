package laricaco.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import laricaco.App;
import laricaco.Usuario;

public class TelaInicialController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Label mensagemLabel;

    @FXML
    private void onContinuar() {
        String login = loginField.getText();
        String senha = senhaField.getText();

        if (login.isEmpty() || senha.isEmpty()) {
            mensagemLabel.setText("Por favor, preencha todos os campos.");
        } else {
            boolean loginValido = false;
            // Lógica de verificação do login
            //Confere se login esta presente na lista de usuarios da classe app, procurando na lista App.usuarios
            for (Usuario usuario : App.usuarios) {
                if (usuario.getLogin().equals(login) && usuario.verificarSenha(senha)) {
                    loginValido = true;
                    break;
                }
            }

            if (loginValido) {
                mensagemLabel.setText("");
                mostrarAlerta("Sucesso", "Login realizado com sucesso!");
                try {
                    App.sistema.mostrarTela("MenuUsuario");
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarErro("Erro ao abrir a tela de Menu.");
                }
            } else {
                mensagemLabel.setText("Login ou senha incorretos ou não existem.");
            }
        }
    }

    @FXML
    private void onCadastrar() {
        mostrarAlerta("Cadastro", "Redirecionando para a tela de cadastro...");
            try {
                // Abre a tela do Menu principal
                App.sistema.mostrarTela("Cadastro");
            } catch (IOException e) {
                e.printStackTrace();
                mostrarErro("Erro ao abrir a tela de Cadastro.");
            }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Método auxiliar para exibir alertas de erro.
     */
    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}