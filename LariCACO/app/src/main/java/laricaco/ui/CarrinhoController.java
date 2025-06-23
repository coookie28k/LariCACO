package laricaco.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import laricaco.App;
import laricaco.Carrinho;
import laricaco.ItemVenda;

/**
 * Controller responsável pela tela de visualização do carrinho de compras.
 * <p>
 * Permite ao usuário visualizar os itens adicionados ao carrinho, ver o subtotal,
 * remover produtos e seguir para a etapa de pagamento. Também permite voltar ao menu principal.
 */
public class CarrinhoController {

    /** Container onde os itens do carrinho são exibidos dinamicamente. */
    @FXML private VBox itensBox;

    /** Label que exibe o subtotal atual do carrinho. */
    @FXML private Label subtotalLabel;

    /**
     * Inicializa a tela do carrinho e atualiza a lista de itens exibidos.
     */
    @FXML
    private void initialize() {
        atualizarLista();
    }

    /**
     * Atualiza a exibição dos itens no carrinho e o subtotal.
     * Remove elementos antigos e reconstrói visualmente a lista com botões de remoção.
     */
    private void atualizarLista() {
        itensBox.getChildren().clear();

        Carrinho carrinho = App.sistema.getLogado().getCarrinho();

        for (ItemVenda item : carrinho.getItens()) {
            GridPane linha = new GridPane();
            linha.setHgap(10);
            linha.setPadding(new Insets(5));

            // Configura colunas com as mesmas proporções do cabeçalho
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(40);
            col1.setHgrow(Priority.ALWAYS);

            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(20);

            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);

            ColumnConstraints col4 = new ColumnConstraints();
            col4.setPercentWidth(20);

            linha.getColumnConstraints().addAll(col1, col2, col3, col4);

            // Nome na coluna 0
            Label nome = new Label(item.getProduto().getNome());
            nome.setMaxWidth(Double.MAX_VALUE);
            GridPane.setHgrow(nome, Priority.ALWAYS);
            linha.add(nome, 0, 0);

            // Quantidade na coluna 1
            Label quantidade = new Label(String.valueOf(item.getQuantidade()));
            linha.add(quantidade, 1, 0);

            // Preço na coluna 2
            Label preco = new Label(String.format("R$ %.2f", item.getTotal()));
            linha.add(preco, 2, 0);

            // Botão Remover na coluna 3
            Button remover = new Button("Remover");
            remover.setOnAction(e -> {
                carrinho.removerItem(item.getProduto());
                atualizarLista();
            });
            linha.add(remover, 3, 0);

            itensBox.getChildren().add(linha);
        }

        subtotalLabel.setText(String.format("R$ %.2f", carrinho.calcularTotal()));
    }

    /**
     * Manipula o evento de clique no botão "Voltar".
     * Redireciona o usuário para o menu principal.
     */
    @FXML
    private void onVoltar() {
        try {
            App.sistema.mostrarTela("MenuUsuario");
        } catch (IOException e) {
            mostrarErro("Erro ao voltar para o menu.");
        }
    }

    /**
     * Manipula o evento de clique no botão "Continuar Compra".
     * Se o carrinho estiver vazio, mostra um alerta. Caso contrário, abre a tela de pagamento.
     */
    @FXML
    private void onContinuarCompra() {
        Carrinho carrinho = App.sistema.getLogado().getCarrinho();

        if (carrinho.getItens().isEmpty()) {
            mostrarAlerta("Seu carrinho está vazio. Adicione produtos antes de continuar a compra.");
        } else {
            try {
                App.sistema.mostrarTela("Pagamento");
            } catch (IOException e) {
                mostrarErro("Erro ao abrir tela de Pagamento.");
            }
        }
    }


    /**
     * Exibe uma caixa de diálogo de informação.
     * 
     * @param mensagem Mensagem a ser exibida
     */
    private void mostrarAlerta(String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Alerta");
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
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
