// ===============================
// VenderController.java
// ===============================
package laricaco.ui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import laricaco.App;
import laricaco.Usuario;
import laricaco.Vendedor;

/**
 * Controller da tela "Vender".
 * <p>
 * Antes de acessar funcionalidades que exigem perfil de vendedor (Meus
 * Produtos / Minhas Vendas), valida se o usuário já é vendedor. Caso não, pede
 * a senha e tenta convertê-lo via {@code SistemaGerenciamento.virarVendedor()}.
 */
public class VenderController {

    /* ---------- Botão Voltar ---------- */
    @FXML
    private void onVoltar() {
        trocarTela("MenuUsuario", "Não foi possível voltar ao menu.");
    }

    /* ---------- Botão Meus Produtos ---------- */
    @FXML
    private void onMeusProdutos() {
        garantirVendedorEExecutar(() -> trocarTela("MeusProdutos", "Não foi possível abrir Meus Produtos."));
    }

    /* ---------- Botão Minhas Vendas ---------- */
    @FXML
    private void onMinhasVendas() {
        garantirVendedorEExecutar(() -> trocarTela("MinhasVendas", "Não foi possível abrir Minhas Vendas."));
    }

    /* ---------- Lógica: garantir que o usuário seja vendedor ---------- */
    private void garantirVendedorEExecutar(Runnable acao) {
        Usuario usuarioLogado = App.sistema.getLogado();

        if (usuarioLogado instanceof Vendedor) {
            acao.run();
            return;
        }

        /* --- Solicita a senha e tenta converter --- */
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Tornar-se Vendedor");
        dialog.setHeaderText("Para acessar esta funcionalidade, você precisa ser vendedor.");
        dialog.setContentText("Confirme sua senha:");

        dialog.showAndWait().ifPresent(senha -> {
            Vendedor v = App.caco.virarVendedor(usuarioLogado, senha);
            if (v != null) {
                App.sistema.setLogado(v); // mantém sessão consistente
                acao.run();                      // prossegue para a tela desejada
            } else {
                mostrarErro("Senha incorreta. Não foi possível tornar-se vendedor.");
            }
        });
    }

    /* ---------- Utilitário de troca de tela ---------- */
    private void trocarTela(String fxml, String erroMsg) {
        try {
            App.sistema.mostrarTela(fxml);
        } catch (IOException e) {
            mostrarErro(erroMsg);
            e.printStackTrace();
        }
    }

    /* ---------- Alerta de erro ---------- */
    private void mostrarErro(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Erro");
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
