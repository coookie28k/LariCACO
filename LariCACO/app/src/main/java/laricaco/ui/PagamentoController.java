package laricaco.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import laricaco.App;
import laricaco.Carrinho;
import laricaco.Exceptions.SaldoInsuficienteException;

/**
 * Controller responsável pela tela de pagamento.
 * <p>
 * Exibe o subtotal do carrinho do usuário logado e permite a confirmação do pagamento,
 * realizando a venda dos itens presentes no carrinho.
 * <p>
 * Após a confirmação do pagamento, desloga o usuário e retorna para a tela inicial.
 */
public class PagamentoController {

    /** Label que exibe o subtotal do carrinho. */
    @FXML
    private Label subtotalLabel;

    /** Elemento gráfico para efeito visual ao clicar no pagamento (não usado diretamente). */
    @FXML
    private StackPane quadradoPagar; // (não é usado diretamente, mas pode receber effects se quiser)

    /**
     * Método chamado na inicialização do controller.
     * Atualiza o label do subtotal com o valor total do carrinho do usuário logado.
     */
    @FXML
    private void initialize() {
        Carrinho carrinho = App.sistema.getLogado().getCarrinho();
        subtotalLabel.setText(String.format("R$ %.2f", carrinho.calcularTotal()));
    }

    /**
     * Ação executada ao clicar no botão ou área de pagamento.
     * <p>
     * Verifica se o carrinho está vazio e, caso contrário, realiza a venda.
     * Após o pagamento, desloga o usuário e retorna para a tela inicial.
     *
     * @throws Exception caso haja erro ao realizar a venda
     */
    @FXML
    private void onPagar() throws Exception {
        Carrinho carrinho = App.sistema.getLogado().getCarrinho();
        if (carrinho.getItens().isEmpty()) {
            mostrarAlerta("Seu carrinho está vazio. Não há nada para pagar.");
            return;
        }

        try {
            App.caco.realizarVenda(App.sistema.getLogado());
            mostrarAlerta("Pagamento realizado com sucesso!");
            //Desliga o usuario
            App.sistema.setLogado(null);
            try {
                App.sistema.mostrarTela("TelaInicial");
            } catch (IOException e) {
                mostrarErro("Erro ao voltar ao início após o pagamento.");
            }
        } catch (SaldoInsuficienteException e){
            mostrarErro(e.getMessage());
        } catch (Exception e) {
            mostrarErro("Erro ao realizar a venda.");
        }    

        

    }

    /**
     * Ação executada ao clicar no botão cancelar.
     * Retorna para a tela do carrinho.
     */
    @FXML
    private void onCancelar() {
        try {
            App.sistema.mostrarTela("CarrinhoCompras");
        } catch (IOException e) {
            mostrarErro("Erro ao voltar para o carrinho.");
        }
    }

    /**
     * Exibe uma caixa de diálogo com a mensagem passada.
     * 
     * @param mensagem Mensagem a ser exibida
     */
    private void mostrarAlerta(String msg) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Pagamento");
        alerta.setHeaderText(null);
        alerta.setContentText(msg);
        alerta.showAndWait();
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
}
