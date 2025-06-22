package laricaco.ui;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import laricaco.App;
import laricaco.Filtros.ItemVendaPorDataFiltro;
import laricaco.ItemVenda;
import laricaco.Produto;
import laricaco.Vendedor;

public class MinhasVendasController {

    @FXML private Button voltarButton;
    @FXML private DatePicker dataInicioPicker;
    @FXML private DatePicker dataFimPicker;
    @FXML private VBox vendasContainer;
    @FXML private Label faturamentoLabel;
    @FXML private Label tituloLabel;

    private Vendedor vendedorLogado;

    @FXML
    public void initialize() {
        // Garantir que usuário é vendedor
        if (!(App.sistema.getLogado() instanceof Vendedor vendedor)) {
            mostrarAlerta("Erro: usuário atual não é um vendedor.");
            // opcional: tentar converter aqui chamando método que você tenha
            return;
        }
        this.vendedorLogado = vendedor;

        // Configura título
        tituloLabel.setText("Minhas Vendas");

        // Datas padrão: últimos 30 dias
        dataFimPicker.setValue(LocalDate.now());
        dataInicioPicker.setValue(LocalDate.now().minusDays(30));

        // Evento para filtrar sempre que data mudar
        dataInicioPicker.valueProperty().addListener((obs, oldV, newV) -> atualizarLista());
        dataFimPicker.valueProperty().addListener((obs, oldV, newV) -> atualizarLista());

        atualizarLista();
    }

    @FXML
    private void onVoltar() {
        try {
            App.sistema.mostrarTela("Vender");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao voltar para a tela de vender.");
        }
    }

    @FXML
    private void onFiltrar() {
        atualizarLista();
    }

    private void atualizarLista() {
        vendasContainer.getChildren().clear();

        LocalDate inicio = dataInicioPicker.getValue();
        LocalDate fim = dataFimPicker.getValue();

        if (inicio == null || fim == null) {
            mostrarErro("Selecione datas válidas.");
            return;
        }

        List<ItemVenda> todas = vendedorLogado.getMinhasVendas();
        List<ItemVenda> filtradas = new ItemVendaPorDataFiltro(inicio, fim).meetFilter(todas);

        if (filtradas.isEmpty()) {
            vendasContainer.getChildren().add(new Label("Nenhuma venda no período selecionado."));
            faturamentoLabel.setText("R$ 0,00");
            return;
        }

        // Agrupar vendas por produto para exibir somatório por produto
        Map<Produto, List<ItemVenda>> agrupadas = filtradas.stream()
            .collect(Collectors.groupingBy(ItemVenda::getProduto));

        double totalPeriodo = 0;

        for (Map.Entry<Produto, List<ItemVenda>> entry : agrupadas.entrySet()) {
            Produto produto = entry.getKey();
            List<ItemVenda> vendasProduto = entry.getValue();

            int quantidadeTotal = vendasProduto.stream().mapToInt(ItemVenda::getQuantidade).sum();
            double totalProduto = vendasProduto.stream().mapToDouble(ItemVenda::getTotal).sum();
            totalPeriodo += totalProduto;

            VBox card = new VBox(5);
            card.setStyle("-fx-border-color: #ccc; -fx-padding: 8; -fx-background-radius: 5; -fx-border-radius: 5;");

            Text nomeProduto = new Text("Produto: " + produto.getNome());
            nomeProduto.setStyle("-fx-font-weight: bold;");
            Text qtd = new Text("Quantidade vendida: " + quantidadeTotal);
            Text precoUnit = new Text(String.format("Preço unitário: R$ %.2f", produto.getPreco()));
            Text totalVenda = new Text(String.format("Total vendido: R$ %.2f", totalProduto));

            card.getChildren().addAll(nomeProduto, qtd, precoUnit, totalVenda);
            vendasContainer.getChildren().add(card);
        }

        faturamentoLabel.setText(String.format("R$ %.2f", totalPeriodo));
    }

    /* ---------- Alertas ---------- */
    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Informação");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}