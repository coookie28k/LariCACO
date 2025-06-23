package laricaco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LariCACOTest {
    SistemaGerenciamento caco = new SistemaGerenciamento(0.1, 0, "caco@mail.com", "senhacaco");
    Usuario cliente;
    Usuario vend;
    Vendedor vendedor;

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

    @Test
    public void venderItens() throws Exception {
        cliente = caco.criarUsuario("cliente@mail.com", "senha123", 200);
        vend = caco.criarUsuario("vend@mail.com", "senha456", 100);
        vendedor = caco.virarVendedor(vend, "senha456");

        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 5, "Brigadeiro!", 10, vendedor);
        brigadeiro.adicionarTag("Contem lactose");

        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);

        cliente.adicionarNoCarrinho(brigadeiro, 2);
        cliente.adicionarNoCarrinho(coxinha, 1);

        caco.realizarVenda(cliente);

        assertEquals(185.0, cliente.getSaldo());
    }

    @Test
    public void precoNaPromocao() throws Exception {
        cliente = caco.criarUsuario("cliente@mail.com", "senha123", 200);
        vend = caco.criarUsuario("vend@mail.com", "senha456", 100);
        vendedor = caco.virarVendedor(vend, "senha456");

        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);
        caco.adicionarPromocao(coxinha, 2, 4);

        cliente.adicionarNoCarrinho(coxinha, 2);

        assertEquals(4.0, cliente.getCarrinho().calcularTotal());
    }

    @Test
    public void filtroPorTag() throws Exception {
        vend = caco.criarUsuario("vend@mail.com", "senha456", 100);
        vendedor = caco.virarVendedor(vend, "senha456");

        Salgado coxinha = caco.cadastrarSalgado("coxinha", 5, "Coxinha!", 10, vendedor);
        coxinha.adicionarTag("Contem carne");
        Salgado paoDeQueijo = caco.cadastrarSalgado("pao de queijo", 4, "Pao de queijo!", 10, vendedor);
        paoDeQueijo.adicionarTag("Vegetariano");
        Doce brigadeiro = caco.cadastrarDoce("brigadeiro", 2, "Brigadeiro", 10, vendedor);
        brigadeiro.adicionarTag("Vegetariano");

        assertEquals(2, caco.filtrarPorTag("Vegetariano").size());
    }

    @Test
    public void filtroPorTipo() throws Exception {
        vend = caco.criarUsuario("vend@mail.com", "senha456", 100);
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
