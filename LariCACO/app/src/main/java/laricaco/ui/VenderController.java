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

    /**
     * Método acionado ao clicar no botão "Voltar".
     * Retornar para a tela do menu do usuário.
     * 
     * @throws IOException se ocorrer erro ao mudar a tela
     */
    @FXML
    private void onVoltar() {
        trocarTela("MenuUsuario", "Não foi possível voltar ao menu.");
    }

    /**
     * Ação associada ao botão "Meus Produtos".
     * Garante que o usuário seja vendedor antes de abrir a tela correspondente.
     */
    @FXML
    private void onMeusProdutos() {
        garantirVendedorEExecutar(() -> trocarTela("MeusProdutos", "Não foi possível abrir Meus Produtos."));
    }

    /**
     * Ação associada ao botão "Minhas Vendas".
     * Garante que o usuário seja vendedor antes de abrir a tela correspondente.
     */
    @FXML
    private void onMinhasVendas() {
        garantirVendedorEExecutar(() -> trocarTela("MinhasVendas", "Não foi possível abrir Minhas Vendas."));
    }

    /**
     * Método auxiliar que verifica se o usuário logado é vendedor.
     * Caso não seja, solicita a senha para tentar converter o usuário em vendedor.
     * Se a conversão for bem-sucedida, executa a ação passada como parâmetro.
     *
     * @param acao ação a ser executada caso o usuário seja (ou se torne) vendedor
     */
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

    /**
     * Método utilitário para troca de telas.
     *
     * @param fxml     nome do arquivo FXML da tela a ser aberta
     * @param erroMsg  mensagem de erro a ser exibida caso a troca falhe
     */
    private void trocarTela(String fxml, String erroMsg) {
        try {
            App.sistema.mostrarTela(fxml);
        } catch (IOException e) {
            mostrarErro(erroMsg);
            e.printStackTrace();
        }
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
