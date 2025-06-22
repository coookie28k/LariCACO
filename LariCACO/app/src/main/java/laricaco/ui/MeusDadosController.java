package laricaco.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import laricaco.App;
import laricaco.Usuario;

public class MeusDadosController {
    @FXML private Label loginLabel;
    @FXML private Label saldoLabel;

    @FXML
    private void initialize() {
        Usuario u = App.sistema.getLogado();
        loginLabel.setText(u.getLogin());
        saldoLabel.setText(String.format("R$ %.2f", u.getSaldo()));
    }

    @FXML
    private void onVoltar() throws IOException {
        try {
            App.sistema.mostrarTela("MenuUsuario");
        } catch (IOException e) {
            mostrarErro("Erro ao voltar ao menu.");
        }
    }

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

        /* ---------- Alerta de erro ---------- */
    private void mostrarErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erro");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }


    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Informação");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
