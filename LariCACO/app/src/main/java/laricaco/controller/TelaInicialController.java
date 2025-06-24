package laricaco.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import laricaco.model.App;
import laricaco.model.Usuario;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Controlador da tela inicial do sistema Laricaco.
 * <p>
 * Essa classe gerencia a interface de login e cadastro inicial,
 * lidando com a entrada do usuário, validação de login,
 * redirecionamento para outras telas e exibição de mensagens.
 */
public class TelaInicialController {

    
    /** Campo de texto para o login do usuário. */
    @FXML
    private TextField loginField;

    /** Campo de senha para o usuário inserir sua senha. */
    @FXML
    private PasswordField senhaField;

    /** Label para exibir mensagens de erro ou informação na tela. */
    @FXML
    private Label mensagemLabel;

    
    /**
     * Método acionado ao clicar no botão "Continuar".
     * Realiza a validação dos campos e autenticação do usuário.
     * Se o login for válido, direciona para a tela do menu do usuário,
     * caso contrário, exibe mensagem de erro.
     */
    @FXML
    private void onContinuar() {
        String login = loginField.getText();
        String senha = senhaField.getText();

        if (login.isEmpty() || senha.isEmpty()) {
            mensagemLabel.setText("Por favor, preencha todos os campos.");
        } else {
            boolean loginValido = false;
            Usuario usuarioEncontrado = null;
            // Lógica de verificação do login
            //Confere se login esta presente na lista de usuarios da classe app, procurando na lista App.usuarios
            for (Usuario usuario : App.caco.getUsuarios()) {
                if (usuario.getLogin().equals(login) && usuario.verificarSenha(senha)) {
                    loginValido = true;
                    usuarioEncontrado = usuario;
                    break;
                }
            }

            if (loginValido) {
                // Define o usuário logado no sistema para uso posterior
                App.sistema.setLogado(usuarioEncontrado);
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

    /**
     * Método acionado ao clicar no botão "Cadastrar".
     * Exibe um alerta informando sobre o redirecionamento
     * e muda para a tela de cadastro de novo usuário.
     */
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

    /**
     * Exibe uma caixa de diálogo de informação com título e mensagem passados.
     * 
     * @param titulo Título da janela de alerta
     * @param mensagem Mensagem a ser exibida
     */
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Exibe uma caixa de diálogo de erro com a mensagem informada.
     * 
     * @param mensagem Mensagem de erro a ser exibida
     */
    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}