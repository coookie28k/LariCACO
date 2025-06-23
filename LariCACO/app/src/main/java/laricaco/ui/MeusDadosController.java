package laricaco.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import laricaco.App;
import laricaco.Usuario;

/**
 * Controlador da tela "Meus Dados".
 * 
 * Esta classe gerencia a exibição dos dados do usuário logado,
 * como login e saldo, e permite atualizar o saldo via input dialog.
 */
public class MeusDadosController {

    /** Label que exibe o login do usuário. */
    @FXML
    private Label loginLabel;

    /** Label que exibe o saldo do usuário. */
    @FXML
    private Label saldoLabel;

    /**
     * Método de inicialização chamado automaticamente após o carregamento do FXML.
     * Atualiza os labels com os dados do usuário logado.
     */
    @FXML
    private void initialize() {
        Usuario u = App.sistema.getLogado();
        loginLabel.setText(u.getLogin());
        saldoLabel.setText(String.format("R$ %.2f", u.getSaldo()));
    }

    /**
     * Método acionado ao clicar no botão "Voltar".
     * Retornar para a tela do menu do usuário.
     * 
     * @throws IOException se ocorrer erro ao mudar a tela
     */
    @FXML
    private void onVoltar() throws IOException {
        try {
            App.sistema.mostrarTela("MenuUsuario");
        } catch (IOException e) {
            mostrarErro("Erro ao voltar ao menu.");
        }
    }

    /**
     * Método acionado ao clicar no botão para atualizar o saldo.
     * Exibe uma caixa de diálogo para o usuário inserir um novo saldo,
     * valida o valor e atualiza o saldo do usuário logado.
     */
    @FXML
    private void onAtualizarSaldo() {
        // Cria o pop-up para digitar o novo saldo
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Atualizar Saldo");
        dialog.setHeaderText("Digite o novo saldo:");
        dialog.setContentText("Novo saldo:");

        // Mostra e espera o usuário clicar Confirmar ou Cancelar
        dialog.showAndWait().ifPresent(input -> {
            try {
                double novoSaldo = Double.parseDouble(input.replace(",", "."));

                if (novoSaldo < 0) {
                    mostrarAlerta("O saldo não pode ser negativo.");
                    return;
                }

                // Atualiza o saldo do usuário logado
                App.sistema.getLogado().setSaldo(novoSaldo);

                // Atualiza o label na tela
                saldoLabel.setText(String.format("R$ %.2f", novoSaldo));

                mostrarAlerta("Saldo atualizado com sucesso!");
            } catch (NumberFormatException e) {
                mostrarAlerta("Valor inválido. Digite um número.");
            }
        });
    }

    /**
     * Exibe uma caixa de diálogo de erro com a mensagem informada.
     * 
     * @param mensagem Mensagem de erro a ser exibida
     */
    private void mostrarErro(String mensagem) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erro");
        a.setHeaderText(null);
        a.setContentText(mensagem);
        a.showAndWait();
    }

    /**
     * Exibe uma caixa de diálogo de informação.
     * 
     * @param mensagem Mensagem a ser exibida
     */
    private void mostrarAlerta(String mensagem) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Informação");
        a.setHeaderText(null);
        a.setContentText(mensagem);
        a.showAndWait();
    }
}
