package laricaco.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import laricaco.model.App;

/**
 * Controlador da tela de menu do usuário.
 * 
 * Essa classe gerencia as ações do menu principal após o login,
 * permitindo navegar entre telas de compra, venda, dados do usuário,
 * além de realizar logout e voltar para a tela inicial.
 */
public class MenuUsuarioController {

    /**
     * Método acionado ao clicar no botão "Voltar".
     * Desloga o usuário atual e retorna para a tela inicial.
     */
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

    /**
     * Método acionado ao clicar no botão "Comprar".
     * Navega para a tela de compra de produtos.
     */
    @FXML
    private void onComprar() {
        try {
            App.sistema.mostrarTela("Comprar");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao abrir tela de comprar");
        }

    }

    /**
     * Método acionado ao clicar no botão "Vender".
     * Navega para a tela de venda de produtos.
     */
    @FXML
    private void onVender() {
        try {
            App.sistema.mostrarTela("Vender");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao abrir tela de vender");
        }
    }

    /**
     * Método acionado ao clicar no botão "Meus Dados".
     * Navega para a tela que exibe os dados do usuário logado.
     */
    @FXML
    private void onMeusDados() {
        try {
            App.sistema.mostrarTela("MeusDados");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao abrir tela de dados do usuário");
        }
    }

    /**
     * Método acionado ao clicar no botão "Deslogar".
     * Desloga o usuário atual e retorna para a tela inicial,
     * exibindo uma mensagem de confirmação.
     */
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

    /**
     * Exibe uma caixa de diálogo de informação com título e mensagem passados.
     * 
     * @param titulo   Título da janela de alerta
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
