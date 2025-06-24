package laricaco.controller;

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
import laricaco.model.App;
import laricaco.model.Filtro;
import laricaco.model.Produto;
import laricaco.model.Tag;
import laricaco.model.Usuario;
import laricaco.model.Exceptions.EstoqueInsuficienteException;
import laricaco.model.Filtros.ProdutoPorTagFiltro;
import laricaco.model.Filtros.ProdutoPorTipoFiltro;

/**
 * Controller da tela de compras, responsável por exibir produtos disponíveis,
 * aplicar filtros por tipo ou tag, e permitir a adição de produtos ao carrinho.
 * <p>
 * A interface permite ajustar a quantidade de produtos antes de adicioná-los,
 * consultar o saldo atual e navegar para o carrinho ou menu principal.
 */
public class ComprarController {

    /** Label que exibe o saldo atual do usuário logado. */
    @FXML private Label saldoLabel;

    /** ComboBox para selecionar o tipo de filtro (por Tipo ou Tag). */
    @FXML private ComboBox<String> filtroTipoCombo;

    /** ComboBox para selecionar o valor do filtro (tipos ou tags). */
    @FXML private ComboBox<String> filtroValorCombo;

    /** Painel onde os produtos são exibidos dinamicamente. */
    @FXML private FlowPane produtosPane;

    /** Lista original de produtos disponíveis no sistema. */
    private List<Produto> listaOriginal;

    /** Mapeamento de nomes de tipos para filtros correspondentes. */
    private final Map<String, Filtro<Produto>> filtrosTipoMap = new HashMap<>();

    /** Mapeamento de nomes de tags para filtros correspondentes. */
    private final Map<String, Filtro<Produto>> filtrosTagMap = new HashMap<>();

    /**
     * Inicializa a tela de compras. Carrega produtos, configura filtros e exibe os itens.
     */
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

    /**
     * Configura a exibição do saldo atual do usuário.
     */
    private void configurarSaldo() {
        Usuario logado = App.sistema.getLogado();
        saldoLabel.setText(String.format("R$ %.2f",
                logado != null ? logado.getSaldo() : 0.0));
    }

    /**
     * Prepara os filtros disponíveis com base nos produtos carregados.
     * Cria filtros por tipo (classe) e por tag.
     */
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

    /**
     * Configura a ComboBox de tipo de filtro.
     * Quando alterado, atualiza as opções disponíveis na segunda ComboBox.
     */
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

    /**
     * Configura a ComboBox de valor do filtro.
     * Aplica o filtro sempre que o valor selecionado mudar.
     */
    private void configurarComboValor() {
        // Aplica o filtro sempre que a 2ª combo mudar
        filtroValorCombo.valueProperty().addListener((o, oldVal, newVal) -> aplicarFiltro());
    }

    /**
     * Aplica o filtro selecionado nos dois ComboBoxes e atualiza a exibição dos produtos.
     */
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

    /**
     * Exibe a lista de produtos no painel gráfico.
     * Os produtos são ordenados alfabeticamente pelo nome.
     *
     * @param lista a lista de produtos a ser exibida
     */
    private void exibirProdutos(List<Produto> lista) {
        produtosPane.getChildren().clear();
        lista.stream()
                .sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER))
                .map(this::criarCardProduto)
                .forEach(produtosPane.getChildren()::add);
    }

    /**
     * Cria visualmente o card de um produto com nome, preço, tags, quantidade e botão de adicionar.
     *
     * @param p o produto a ser representado graficamente
     * @return o nó JavaFX representando o card
     */
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

        //para aparecer tags
        FlowPane tagsPane = new FlowPane(4, 4);
        tagsPane.setPrefWrapLength(140);
        tagsPane.setStyle("-fx-padding:4 0 4 0;");

        for (Tag t : p.getTag()) {
            Label tagLabel = new Label(t.getTag());
            tagLabel.setStyle("""
                -fx-background-color:#e0e0e0;
                -fx-background-radius:12;
                -fx-padding:2 6 2 6;
                -fx-font-size:10px;
            """);
            tagsPane.getChildren().add(tagLabel);
        }


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

        card.getChildren().addAll(nome, preco, tagsPane, quantidadeBox, add);
        return card;
    }

    /**
     * Tenta adicionar um produto ao carrinho na quantidade especificada.
     *
     * @param p o produto a ser adicionado
     * @param quantidade a quantidade desejada
     */
    private void adicionarAoCarrinho(Produto p, int quantidade) {
        try {
            App.sistema.getLogado().adicionarNoCarrinho(p, quantidade);
            mostrarAlerta("Carrinho", p.getNome() + " adicionado!");
        } catch (EstoqueInsuficienteException e) {
            mostrarErro(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            mostrarErro("Erro ao adicionar ao carrinho.");
            
        }
    }

        /**
     * Método acionado ao clicar no botão "Voltar".
     * Desloga o usuário atual e retorna para a tela do menu do usuário.
     */
    @FXML
    private void onVoltar() {
        trocarTela("MenuUsuario", "Não foi possível voltar ao menu.");
    }

    /**
     * Vai para a tela do carrinho de compras.
     */
    @FXML
    private void onCarrinho() {
        try {
            App.sistema.mostrarTela("CarrinhoCompras");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Não foi possível abrir o carrinho.");
        }
    }

    /**
     * Tenta trocar a tela para o arquivo FXML especificado, mostrando erro caso falhe.
     *
     * @param fxml nome da tela FXML
     * @param erroMsg mensagem de erro a ser mostrada caso falhe
     */
    private void trocarTela(String fxml, String erroMsg) {
        try {
            App.sistema.mostrarTela(fxml);
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro(erroMsg);
        }
    }

    /**
     * Exibe uma caixa de diálogo de informação com título e mensagem passados.
     * 
     * @param t Título da janela de alerta
     * @param m Mensagem a ser exibida
     */
    private void mostrarAlerta(String t, String m) {
        alert(Alert.AlertType.INFORMATION, t, m);
    }

    /**
     * Exibe uma caixa de diálogo de erro com a mensagem informada.
     * 
     * @param m Mensagem de erro a ser exibida
     */
    private void mostrarErro(String m) {
        alert(Alert.AlertType.ERROR, "Erro", m);
    }

    /**
     * Exibe uma caixa de diálogo genérica.
     *
     * @param tipo o tipo do alerta (informação, erro, etc.)
     * @param titulo título da janela
     * @param msg mensagem a ser exibida
     */
    private void alert(Alert.AlertType tipo, String titulo, String msg) {
        Alert a = new Alert(tipo);
        a.setTitle(titulo);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
