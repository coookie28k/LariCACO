package laricaco.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import laricaco.App;
import laricaco.Exceptions.EstoqueInsuficienteException;
import laricaco.Filtro;
import laricaco.Filtros.ProdutoPorTagFiltro;
import laricaco.Filtros.ProdutoPorTipoFiltro;
import laricaco.Produto;
import laricaco.Tag;
import laricaco.Usuario;

public class ComprarController {

    /* --------------------------- FXML --------------------------- */
    @FXML
    private Label saldoLabel;
    @FXML
    private ComboBox<String> filtroTipoCombo; // NOVO
    @FXML
    private ComboBox<String> filtroValorCombo; // NOVO
    @FXML
    private FlowPane produtosPane;

    /* -------------------- dados e caches ------------------------ */
    private List<Produto> listaOriginal;
    private final Map<String, Filtro<Produto>> filtrosTipoMap = new HashMap<>();
    private final Map<String, Filtro<Produto>> filtrosTagMap = new HashMap<>();

    /* ----------------------- init ------------------------------- */
    @FXML
    private void initialize() {
        listaOriginal = App.caco.getProdutos().stream()
                .filter(p -> p.getEstoque() > 0)
                .collect(Collectors.toList());

        configurarSaldo();
        prepararFiltrosDisponiveis(); // enche mapas
        configurarComboTipo(); // popula primeiro combo
        configurarComboValor(); // popula/ouve segundo combo

        exibirProdutos(listaOriginal); // tudo à primeira vista
    }

    /* ---- saldo ---- */
    private void configurarSaldo() {
        Usuario logado = App.sistema.getLogado();
        saldoLabel.setText(String.format("R$ %.2f",
                logado != null ? logado.getSaldo() : 0.0));
    }

    /* ---- prepara mapas de filtros ---- */
    private void prepararFiltrosDisponiveis() {
        /* ---------- por TIPO (classe concreta) ---------- */
        Set<Class<? extends Produto>> tipos = listaOriginal.stream()
                .map(Produto::getClass)
                .collect(Collectors.toSet());

        filtrosTipoMap.put("Todos", l -> l); // identidade
        tipos.forEach(c -> filtrosTipoMap.put(
                c.getSimpleName(), new ProdutoPorTipoFiltro(c)));

        /* ---------- por TAG ---------- */
        Set<String> tags = listaOriginal.stream()
                .flatMap(p -> p.getTag().stream())
                .map(Tag::getTag)
                .collect(Collectors.toSet());

        filtrosTagMap.put("Todos", l -> l);
        tags.forEach(t -> filtrosTagMap.put(
                t, new ProdutoPorTagFiltro(t)));
    }

    /* ---- ComboBox 1 (Tipo do filtro) ---- */
    private void configurarComboTipo() {
        filtroTipoCombo.setItems(FXCollections.observableArrayList(
                "Todos", "Tipo", "Tag"));
        filtroTipoCombo.getSelectionModel().select("Todos");

        // Muda as opções do 2º combo toda vez que tipo muda
        filtroTipoCombo.valueProperty().addListener((o, oldVal, newVal) -> {
            if (newVal == null)
                return;
            List<String> valores;
            switch (newVal) {
                case "Tipo" -> valores = new ArrayList<>(filtrosTipoMap.keySet());
                case "Tag" -> valores = new ArrayList<>(filtrosTagMap.keySet());
                default -> valores = List.of("Todos");
            }
            filtroValorCombo.setItems(FXCollections.observableArrayList(valores));
            filtroValorCombo.getSelectionModel().select("Todos");
            aplicarFiltro(); // exibe de acordo com novo default
        });
    }

    /* ---- ComboBox 2 (Valor do filtro) ---- */
    private void configurarComboValor() {
        // Aplica o filtro sempre que a 2ª combo mudar
        filtroValorCombo.valueProperty().addListener((o, oldVal, newVal) -> aplicarFiltro());
    }

    /* ---- Aplica filtro conforme ambos combos ---- */
    private void aplicarFiltro() {
        String tipoSelecionado = filtroTipoCombo.getValue();
        String valorSelecionado = filtroValorCombo.getValue();

        if (tipoSelecionado == null || valorSelecionado == null || "Todos".equals(tipoSelecionado)) {
            exibirProdutos(listaOriginal);
            return;
        }

        Filtro<Produto> filtro;
        if ("Tipo".equals(tipoSelecionado)) {
            filtro = filtrosTipoMap.getOrDefault(valorSelecionado, l -> l);
        } else { // Tag
            filtro = filtrosTagMap.getOrDefault(valorSelecionado, l -> l);
        }
        exibirProdutos(filtro.meetFilter(listaOriginal));
    }

    /* ---- render de produtos ---- */
    private void exibirProdutos(List<Produto> lista) {
        produtosPane.getChildren().clear();
        lista.stream()
                .sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER))
                .map(this::criarCardProduto)
                .forEach(produtosPane.getChildren()::add);
    }

    private Node criarCardProduto(Produto p) {
        VBox card = new VBox(5);
        card.setStyle("""
                    -fx-padding:10;
                    -fx-border-color:#c0c0c0;
                    -fx-border-radius:6;
                    -fx-background-radius:6;
                    -fx-background-color:#fafafa;
                """);
        card.setPrefWidth(160);

        Text nome = new Text(p.getNome());
        nome.setStyle("-fx-font-weight:bold;");

        Text preco = new Text(String.format("R$ %.2f", p.getPreco()));

        // Quantidade com mínimo 1
        Label quantidadeLabel = new Label("1");
        quantidadeLabel.setStyle("-fx-font-size: 14px; -fx-min-width: 30px; -fx-alignment: center;");

        final int[] quantidade = { 1 };

        Button menos = new Button("-");
        Button mais = new Button("+");

        menos.setOnAction(e -> {
            if (quantidade[0] > 1) {
                quantidade[0]--;
                quantidadeLabel.setText(String.valueOf(quantidade[0]));
            }
        });

        mais.setOnAction(e -> {
            if (quantidade[0] < p.getEstoque()) {
                quantidade[0]++;
                quantidadeLabel.setText(String.valueOf(quantidade[0]));
            }
        });

        // Controles de quantidade em linha: [-] [1] [+]
        HBox quantidadeBox = new HBox(5, menos, quantidadeLabel, mais);
        quantidadeBox.setStyle("-fx-alignment: center;");

        Button add = new Button("Adicionar");
        add.setMaxWidth(Double.MAX_VALUE);
        add.setOnAction(e -> {
            try {
                adicionarAoCarrinho(p, quantidade[0]);
            } catch (Exception ex) {
                mostrarErro("Estoque insuficiente!");
            }
        });

        card.getChildren().addAll(nome, preco, quantidadeBox, add);
        return card;
    }

    private void adicionarAoCarrinho(Produto p, int quantidade) {
        try {
            App.sistema.getLogado().adicionarNoCarrinho(p, quantidade);
            mostrarAlerta("Carrinho", p.getNome() + " adicionado!");
        } catch (EstoqueInsuficienteException e) {
            mostrarErro("Estoque insuficiente para " + p.getNome() + ".");
        } catch (Exception e) {
            mostrarErro("Erro ao adicionar ao carrinho.");
            e.printStackTrace();
        }
    }

    /* ---- navegação ---- */
    @FXML
    private void onVoltar() {
        trocarTela("MenuUsuario", "Não foi possível voltar ao menu.");
    }

    @FXML
    private void onCarrinho() {
        try {
            App.sistema.mostrarTela("CarrinhoCompras");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Não foi possível abrir o carrinho.");
        }
    }

    private void trocarTela(String fxml, String erroMsg) {
        try {
            App.sistema.mostrarTela(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro(erroMsg);
        }
    }

    /* ---- diálogos ---- */
    private void mostrarAlerta(String t, String m) {
        alert(Alert.AlertType.INFORMATION, t, m);
    }

    private void mostrarErro(String m) {
        alert(Alert.AlertType.ERROR, "Erro", m);
    }

    private void alert(Alert.AlertType tipo, String titulo, String msg) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
