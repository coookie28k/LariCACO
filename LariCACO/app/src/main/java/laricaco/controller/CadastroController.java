package laricaco.controller;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import laricaco.model.App;
import laricaco.model.Usuario;
import laricaco.model.Exceptions.LoginJaExistenteException;

/**
 * Controller responsável pela tela de cadastro de novos usuários.
 * <p>
 * Permite que o usuário informe login, senha e saldo inicial para criar uma nova conta.
 * Realiza validações de campos obrigatórios, verifica se o login já existe e converte o saldo informado.
 * <p>
 * Após o cadastro bem-sucedido, exibe mensagem de confirmação e retorna para a tela inicial (login).
 */
public class CadastroController {

    /** 
     * Campo de texto onde o usuário digita seu login. 
     */
    @FXML
    private TextField loginField;

    /** 
     * Campo de senha onde o usuário digita sua senha. 
     */
    @FXML
    private PasswordField senhaField;

    
    /** 
     * Campo de texto onde o usuário informa o saldo inicial. 
     */
    @FXML
    private TextField saldoField;

    /** 
     * Rótulo usado para exibir mensagens de erro ou confirmação na tela de cadastro. 
     */
    @FXML
    private Label mensagemLabel;

    /**
     * Método acionado ao clicar no botão "Voltar".
     * Retorna para a tela inicial.
     */
    @FXML
    private void onVoltar() {
        try {
            App.sistema.mostrarTela("TelaInicial");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao voltar para a tela inicial.");
        }
    }

    /**
     * Evento chamado ao clicar no botão "Continuar".
     * Valida os dados do formulário, cria novo usuário caso os dados estejam corretos
     * e exibe mensagens de erro ou sucesso correspondentes.
     */
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
        Optional<Usuario> existente = App.caco.getUsuarios().stream()
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
        try {
            App.caco.criarUsuario(login, senha, saldoInicial);
            mostrarAlerta("Cadastro concluído",
                      String.format("Usuário %s criado com sucesso!", login));
            // Redireciona para a tela de login
             onVoltar();
        } catch (LoginJaExistenteException e) {
            mostrarErro(e.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro no cadastro do usuário.");
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
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    

    

}
