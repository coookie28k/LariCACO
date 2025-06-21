package laricaco.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

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
            // Aqui você pode colocar a lógica de verificação do login
            if (login.equals("admin") && senha.equals("1234")) {
                mensagemLabel.setText("");
                showAlert("Sucesso", "Login realizado com sucesso!");
            } else {
                mensagemLabel.setText("Login ou senha incorretos.");
            }
        }
    }

    @FXML
    private void onCadastrar() {
        showAlert("Cadastro", "Redirecionando para a tela de cadastro...");
        // Aqui você pode adicionar a lógica para mudar para a tela de cadastro
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}