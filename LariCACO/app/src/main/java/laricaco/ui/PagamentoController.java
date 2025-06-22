package laricaco.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import laricaco.App;
import laricaco.Carrinho;

public class PagamentoController {

    @FXML
    private Label subtotalLabel;
    @FXML
    private StackPane quadradoPagar; // (não é usado diretamente, mas pode receber effects se quiser)

    /* ---------- Inicialização ---------- */
    @FXML
    private void initialize() {
        Carrinho carrinho = App.sistema.getLogado().getCarrinho();
        subtotalLabel.setText(String.format("R$ %.2f", carrinho.calcularTotal()));
    }

    /* ---------- Clique no quadrado ---------- */
    @FXML
    private void onPagar() throws Exception {
        Carrinho carrinho = App.sistema.getLogado().getCarrinho();
        if (carrinho.getItens().isEmpty()) {
            mostrarAlerta("Seu carrinho está vazio. Não há nada para pagar.");
            return;
        }

        App.caco.realizarVenda(App.sistema.getLogado());

        // FALTA IMPLEMENTAR public void realizarVenda(Usuario cliente, Vendedor
        // vendedor)

        mostrarAlerta("Pagamento realizado com sucesso!");

        try {
            App.sistema.mostrarTela("TelaInicial");
        } catch (IOException e) {
            mostrarErro("Erro ao voltar ao início após o pagamento.");
        }
    }

    /* ---------- Botão Cancelar ---------- */
    @FXML
    private void onCancelar() {
        try {
            App.sistema.mostrarTela("TelaCarrinho");
        } catch (IOException e) {
            mostrarErro("Erro ao voltar para o carrinho.");
        }
    }

    /* ---------- Utilitários ---------- */
    private void mostrarAlerta(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Pagamento");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
    }

    private void mostrarErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erro");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
