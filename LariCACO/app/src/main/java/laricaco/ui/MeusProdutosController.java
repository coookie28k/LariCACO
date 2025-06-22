package laricaco.ui;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import laricaco.App;
import laricaco.Produto;
import laricaco.Tag;
import laricaco.Usuario;
import laricaco.Vendedor;

/**
 * Controller da tela “Meus Produtos”.
 * <p>
 * Agora mostra apenas os produtos do vendedor logado. Se o usuário logado ainda
 * não for um {@link Vendedor}, o controller solicita a senha e tenta
 * convertê‑lo chamando {@code SistemaGerenciamento.virarVendedor()}.
 */
public class MeusProdutosController {

    /* ------------ FXML ------------ */
    @FXML private FlowPane produtosPane;

    /* ------------ Estado ------------ */
    private Usuario usuarioLogado;

    /* ------------ Inicialização ------------ */
    @FXML
    private void initialize() {
        if (!(App.sistema.getLogado() instanceof Vendedor vendedor)) {
            mostrarAlerta("Erro: usuário atual não é um vendedor.");
            return;
        }

        exibirProdutos(vendedor.getMeusProdutos());
    }

    /**
     * Solicita a senha ao usuário e tenta convertê‑lo em Vendedor.
     */
    private void tentarVirarVendedor() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tornar‑se Vendedor");
        dialog.setHeaderText("Para gerenciar produtos é necessário ser vendedor.");
        dialog.setContentText("Confirme sua senha:");

        dialog.showAndWait().ifPresent(senha -> {
            Vendedor v = App.caco.virarVendedor(usuarioLogado, senha);
            if (v != null) {
                usuarioLogado = v;
                App.sistema.setLogado(v);   // mantém sessão consistente
            } else {
                mostrarAlerta("Senha incorreta. Não foi possível tornar‑se vendedor.");
            }
        });
    }

    /* ------------ Exibição ------------ */
    private void exibirProdutos(List<Produto> lista) {
        produtosPane.getChildren().clear();

        lista.stream()
             .sorted(Comparator.comparing(Produto::getNome, String.CASE_INSENSITIVE_ORDER))
             .map(this::criarCardProduto)
             .forEach(produtosPane.getChildren()::add);
    }

    /* Cria um card com Nome, Estoque e botão Editar */
    private Node criarCardProduto(Produto p) {
        VBox card = new VBox(6);
        card.setPrefWidth(160);
        card.setStyle("""
            -fx-padding:10; -fx-border-color:#c0c0c0; -fx-border-radius:6;
            -fx-background-radius:6; -fx-background-color:#fafafa;""");

        Text nome = new Text(p.getNome());
        nome.setStyle("-fx-font-weight:bold;");

        Label estoque = new Label("Estoque: " + p.getEstoque());

        Button editar = new Button("Editar produto");
        editar.setMaxWidth(Double.MAX_VALUE);
        editar.setOnAction(e -> abrirPopupEdicao(p));

        card.getChildren().addAll(nome, estoque, editar);
        return card;
    }

    /* ------------ Popup de edição ------------ */
    /** Abre um diálogo para editar TODOS os atributos do produto. */
    private void abrirPopupEdicao(Produto p) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Editar Produto");
        dialog.setHeaderText("Editar atributos de \"" + p.getNome() + "\"");

        // ---------- Campos ----------
        TextField nomeField = new TextField(p.getNome());
        TextField precoField = new TextField(String.valueOf(p.getPreco()));
        TextArea descArea = new TextArea(p.getDescricao());
        descArea.setPrefRowCount(3);
        TextField estoqueField = new TextField(String.valueOf(p.getEstoque()));

        ListView<Tag> tagList = new ListView<>();
        tagList.getItems().addAll(p.getTag());

        TextField novaTagField = new TextField();
        novaTagField.setPromptText("Nova tag");
        Button addTagBtn = new Button("Adicionar Tag");
        addTagBtn.setOnAction(e -> {
            String txt = novaTagField.getText().trim();
            if (!txt.isEmpty()) {
                Tag t = new Tag(txt);
                tagList.getItems().add(t);
                novaTagField.clear();
            }
        });

        // ---------- Layout ----------
        GridPane grid = new GridPane();
        grid.setVgap(8);
        grid.setHgap(10);
        int row = 0;
        grid.add(new Label("Nome:"), 0, row); grid.add(nomeField, 1, row++);
        grid.add(new Label("Preço (R$):"), 0, row); grid.add(precoField, 1, row++);
        grid.add(new Label("Descrição:"), 0, row); grid.add(descArea, 1, row++);
        grid.add(new Label("Estoque:"), 0, row); grid.add(estoqueField, 1, row++);
        grid.add(new Label("Tags:"), 0, row); grid.add(tagList, 1, row++);
        grid.add(novaTagField, 1, row);
        grid.add(addTagBtn, 2, row);

        dialog.getDialogPane().setContent(grid);

        // Remove os botões padrões
        dialog.getDialogPane().getButtonTypes().clear();

        // Cria os botões personalizados
        ButtonType confirmarBtnType = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelarBtnType = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType removerBtnType = new ButtonType("Remover Produto", ButtonBar.ButtonData.LEFT);

        dialog.getDialogPane().getButtonTypes().addAll(removerBtnType, confirmarBtnType, cancelarBtnType);

        // ---------- Ação dos botões ----------
        dialog.showAndWait().ifPresent(bt -> {
            if (bt == confirmarBtnType) {
                try {
                    String novoNome = nomeField.getText().trim();
                    double novoPreco = Double.parseDouble(precoField.getText().replace(",", "."));
                    int novoEstoque = Integer.parseInt(estoqueField.getText().trim());

                    if (novoNome.isEmpty() || novoPreco < 0 || novoEstoque < 0) {
                        throw new IllegalArgumentException();
                    }

                    p.setNome(novoNome);
                    p.setPreco(novoPreco);
                    p.setDescricao(descArea.getText());
                    p.setEstoque(novoEstoque);

                    p.getTag().clear();
                    tagList.getItems().forEach(p::adicionarTag);

                    if (usuarioLogado instanceof Vendedor v) {
                        exibirProdutos(v.getMeusProdutos());
                    }

                } catch (IllegalArgumentException ex) {
                    mostrarAlerta("Campos inválidos. Verifique nome, preço e estoque.");
                }

            } else if (bt == removerBtnType) {
                //falta ainda ter um pop de confirmacao falando pra retirar todos os produtos do estoque para remover o produto
                if (usuarioLogado instanceof Vendedor v) {
                    v.removerProduto(p);
                    exibirProdutos(v.getMeusProdutos());
                    mostrarAlerta("Produto removido com sucesso.");
                } else {
                    mostrarAlerta("Apenas vendedores podem remover produtos.");
                }
            }
        });
    }


    /* ------------ Botões ------------ */
    @FXML
    private void onVoltar() { trocarTela("Vender", "Não foi possível voltar."); }

    @FXML
    private void onAdicionarProduto() {
        /* 
        // 1) Só vendedores podem criar:
        if (!(usuarioLogado instanceof Vendedor v)) {
            mostrarAlerta("Apenas vendedores podem adicionar produtos.");
            return;
        }

        // ---------- Diálogo
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Produto");
        dialog.setHeaderText("Informe os dados do novo produto");

        // -- campos
        TextField nomeField    = new TextField();
        TextField precoField   = new TextField();
        TextArea  descArea     = new TextArea(); descArea.setPrefRowCount(3);
        TextField estoqueField = new TextField();

        // -- tags
        ListView<Tag> tagList  = new ListView<>();
        TextField novaTagField = new TextField(); novaTagField.setPromptText("Nova tag");
        Button addTagBtn = new Button("Adicionar Tag");
        addTagBtn.setOnAction(e -> {
            String txt = novaTagField.getText().trim();
            if (!txt.isEmpty()) {
                tagList.getItems().add(new Tag(txt));
                novaTagField.clear();
            }
        });

        // -- layout
        GridPane grid = new GridPane(); grid.setVgap(8); grid.setHgap(10);
        int row = 0;
        grid.add(new Label("Nome:"),       0, row); grid.add(nomeField,    1, row++);
        grid.add(new Label("Preço (R$):"), 0, row); grid.add(precoField,   1, row++);
        grid.add(new Label("Descrição:"),  0, row); grid.add(descArea,     1, row++);
        grid.add(new Label("Estoque:"),    0, row); grid.add(estoqueField, 1, row++);
        grid.add(new Label("Tags:"),       0, row); grid.add(tagList,      1, row++);
        grid.add(novaTagField,             1, row); grid.add(addTagBtn,    2, row);

        dialog.getDialogPane().setContent(grid);

        // -- botões
        ButtonType confirmarBtnType = new ButtonType("Confirmar", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelarBtnType  = new ButtonType("Cancelar",  ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().setAll(confirmarBtnType, cancelarBtnType);

        // ---------- ação "Confirmar" ---------- 
        dialog.showAndWait().ifPresent(bt -> {
            if (bt == confirmarBtnType) {
                try {
                    String nome   = nomeField.getText().trim();
                    double preco  = Double.parseDouble(precoField.getText().replace(",", "."));
                    int estoque   = Integer.parseInt(estoqueField.getText().trim());

                    if (nome.isEmpty() || preco < 0 || estoque < 0) {
                        throw new IllegalArgumentException();
                    }

                    // 2) Cria produto com id válido
                    int novoId = gerarNovoId();   //↩ troque por sua estratégia real
                    Produto novo = new Produto(3, nome, preco, descArea.getText(), estoque);

                    // 3) Seta vendedor e tags
                    novo.setVendedor(v);
                    tagList.getItems().forEach(novo::adicionarTag);

                    // 4) Persiste e atualiza UI
                    v.adicionarProduto(novo);
                    exibirProdutos(v.getMeusProdutos());

                    mostrarAlerta("Produto adicionado com sucesso.");

                } catch (NumberFormatException | IllegalArgumentException ex) {
                    mostrarAlerta("Campos inválidos. Verifique nome, preço e estoque.");
                }
            }
        });
        */
    }

    /* ------------ Utilidades ------------ */
    private void trocarTela(String fxml, String erroMsg) {
        try { App.sistema.mostrarTela(fxml); }
        catch (IOException e) { mostrarAlerta(erroMsg); e.printStackTrace(); }
    }

    private void mostrarAlerta(String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Informação");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
