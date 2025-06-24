package laricaco.controller;

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
import laricaco.model.App;
import laricaco.model.ItemVenda;
import laricaco.model.Produto;
import laricaco.model.Vendedor;
import laricaco.model.Filtros.ItemVendaPorDataFiltro;

/**
 * Controller responsável pela tela "Minhas Vendas".
 * <p>
 * Permite ao vendedor logado visualizar suas vendas dentro de um período definido.
 * As vendas são filtradas por data e agrupadas por produto, exibindo informações como
 * quantidade vendida, preço unitário e total arrecadado por produto.
 * Também exibe o faturamento total no período.
 */
public class MinhasVendasController {

    /** Botão para voltar à tela anterior. */
    @FXML private Button voltarButton;

    /** Seletor de data de início do filtro de vendas. */
    @FXML private DatePicker dataInicioPicker;

    /** Seletor de data de fim do filtro de vendas. */
    @FXML private DatePicker dataFimPicker;

    /** Container onde os cards de vendas serão exibidos. */
    @FXML private VBox vendasContainer;

    /** Label que exibe o valor total das vendas no período. */
    @FXML private Label faturamentoLabel;

    /** Label que exibe o título da tela. */
    @FXML private Label tituloLabel;

    /** Vendedor atualmente logado no sistema. */
    private Vendedor vendedorLogado;

    /**
     * Inicializa a tela de vendas. Define as datas padrão,
     * carrega as vendas e configura o comportamento dos filtros.
     */
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

    /**
     * Retorna para a tela anterior (tela "Vender").
     */
    @FXML
    private void onVoltar() {
        try {
            App.sistema.mostrarTela("Vender");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao voltar para a tela de vender.");
        }
    }

    /**
     * Atualiza a lista de vendas com base nas datas selecionadas.
     */
    @FXML
    private void onFiltrar() {
        atualizarLista();
    }

    /**
     * Atualiza dinamicamente a exibição das vendas filtradas.
     */
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

    /** Exibe uma caixa de diálogo de informação.
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