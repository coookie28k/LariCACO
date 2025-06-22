package laricaco.ui;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import laricaco.App;
import laricaco.Usuario;

public class CadastroController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private TextField saldoField;

    @FXML
    private Label mensagemLabel;

    /* ---------- Botão Voltar ---------- */
    @FXML
    private void onVoltar() {
        try {
            App.sistema.mostrarTela("TelaInicial");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao voltar para a tela inicial.");
        }
    }

    /* ---------- Botão Continuar ---------- */
    @FXML
    private void onContinuar() {
        String login = loginField.getText().trim();
        String senha = senhaField.getText();
        String saldoTexto = saldoField.getText().trim();

        // Validações básicas
        if (login.isEmpty() || senha.isEmpty() || saldoTexto.isEmpty()) {
            mensagemLabel.setText("Preencha todos os campos.");
            return;
        }

        // Verifica se login já existe
        Optional<Usuario> existente = App.usuarios.stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst();

        if (existente.isPresent()) {
            mensagemLabel.setText("Login já utilizado. Escolha outro.");
            return;
        }

        // Converte saldo
        double saldoInicial;
        try {
            saldoInicial = Double.parseDouble(saldoTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            mensagemLabel.setText("Saldo inválido.");
            return;
        }

        // Cria usuário e adiciona à lista
        Usuario novo = new Usuario(login, senha, saldoInicial);
        App.usuarios.add(novo);

        mostrarAlerta("Cadastro concluído",
                      String.format("Usuário %s criado com sucesso!", login));

        // Redireciona para a tela de login
        onVoltar();
    }

    /* ---------- Métodos auxiliares ---------- */
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
