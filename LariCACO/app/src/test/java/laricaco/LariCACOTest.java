package laricaco;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Testes unitários para o sistema de gerenciamento Laricaco.
 * 
 * Esta classe realiza testes para verificar funcionalidades como:
 * adicionar itens ao carrinho, realizar vendas, aplicar promoções
 * e filtrar produtos por tag e por tipo.
 */
public class LariCACOTest {
    SistemaGerenciamento caco = SistemaGerenciamento.getInstance(0.1, 0, "caco@mail.com", "senhacaco");
    Usuario cliente;
    Usuario vend;
    Vendedor vendedor;

    /**
     * Testa a adição de um item ao carrinho de um usuário,
     * verificando se o total do carrinho é calculado corretamente.
     * 
     * @throws Exception caso ocorra erro ao adicionar o item
     */
    @Test
    public void adicionarItemNoCarrinho() throws Exception {
        cliente = caco.criarUsuario("cliente@mail.com", "senha123", 200);
        vend = caco.criarUsuario("vend@mail.com", "senha456", 100);
        vendedor = caco.virarVendedor(vend, "senha456");

        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 5, "Brigadeiro!", 10, vendedor);
        brigadeiro.adicionarTag("Contem lactose");

        cliente.adicionarNoCarrinho(brigadeiro, 2);
        assertEquals(10.0, cliente.getCarrinho().calcularTotal());
    }

    /**
     * Testa o processo de venda de itens no carrinho de um cliente,
     * verificando se o saldo do cliente é atualizado corretamente após a compra.
     * 
     * @throws Exception caso ocorra erro na realização da venda
     */
    @Test
    public void venderItens() throws Exception {
        cliente = caco.criarUsuario("marcobbueno", "senha123", 200);
        vend = caco.criarUsuario("vend2", "senha456", 100);
        vendedor = caco.virarVendedor(vend, "senha456");

        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 5, "Brigadeiro!", 10, vendedor);
        brigadeiro.adicionarTag("Contem lactose");

        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);

        cliente.adicionarNoCarrinho(brigadeiro, 2);
        cliente.adicionarNoCarrinho(coxinha, 1);

        caco.realizarVenda(cliente);

        assertEquals(185.0, cliente.getSaldo());
    }

    /**
     * Testa o cálculo do preço com promoção aplicada em um produto,
     * verificando se o total do carrinho considera corretamente a promoção.
     * 
     * @throws Exception caso ocorra erro ao adicionar o item ou aplicar a promoção
     */
    @Test
    public void precoNaPromocao() throws Exception {
        cliente = caco.criarUsuario("cliente52", "senha123", 200);
        vend = caco.criarUsuario("vend4", "senha456", 100);
        vendedor = caco.virarVendedor(vend, "senha456");

        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);
        caco.adicionarPromocao(coxinha, 2, 4);

        cliente.adicionarNoCarrinho(coxinha, 2);

        assertEquals(4.0, cliente.getCarrinho().calcularTotal());
    }

    /**
     * Testa o filtro de produtos por tag, verificando se o sistema retorna
     * a quantidade correta de produtos que possuem determinada tag.
     * 
     * @throws Exception caso ocorra erro na criação dos produtos ou vendedores
     */
    @Test
    public void filtroPorTag() throws Exception {
        vend = caco.criarUsuario("vend5", "senha456", 100);
        vendedor = caco.virarVendedor(vend, "senha456");

        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);
        coxinha.adicionarTag("Contem carne");
        Salgado paoDeQueijo = caco.cadastrarSalgado("pao de queijo", 4, "Pao de queijo!", 10, vendedor);
        paoDeQueijo.adicionarTag("Vegetariano");
        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 2, "Brigadeiro", 10, vendedor);
        brigadeiro.adicionarTag("Vegetariano");

        assertEquals(2, caco.filtrarPorTag("Vegetariano").size());
    }

    /**
     * Testa o filtro de produtos por tipo, verificando se o sistema retorna
     * corretamente a quantidade de produtos de um tipo específico.
     * 
     * @throws Exception caso ocorra erro na criação dos produtos ou vendedores
     */
    @Test
    public void filtroPorTipo() throws Exception {
        vend = caco.criarUsuario("vend@6", "senha456", 100);
        vendedor = caco.virarVendedor(vend, "senha456");

        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);
        coxinha.adicionarTag("Contem carne");
        Salgado paoDeQueijo = caco.cadastrarSalgado("pao de queijo", 4, "Pao de queijo!", 10, vendedor);
        paoDeQueijo.adicionarTag("Vegetariano");
        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 2, "Brigadeiro", 10, vendedor);
        brigadeiro.adicionarTag("Vegetariano");

        assertEquals(1, caco.filtrarPorTipo(Doce.class).size());
    }
}
