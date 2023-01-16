package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    @Parameter
    public List<Filme> filmes;

    @Parameter(value = 1)
    public Double valorLocacao;

    @Parameter(value = 2)
    public String descricao;

    private LocacaoService locacaoService;
    private LocacaoDao locacaoDao;
    private SpcService spcService;

    @Before
    public void setup() {
        locacaoService = new LocacaoService();

        locacaoDao = Mockito.mock(LocacaoDao.class);
        locacaoService.setLocacaoDao(locacaoDao);

        spcService = Mockito.mock(SpcService.class);
        locacaoService.setSpcService(spcService);
    }

    private static Filme primeiroFilme = new Filme("Filme 1", 1, 100.0);
    private static Filme segundoFilme = new Filme("Filme 2", 1, 100.0);
    private static Filme terceiroFilme = new Filme("Filme 3", 1, 100.0);
    private static Filme quartoFilme = new Filme("Filme 4", 1, 100.0);
    private static Filme quintoFilme = new Filme("Filme 5", 1, 100.0);
    private static Filme sextoFilme = new Filme("Filme 6", 1, 100.0);
    private static Filme setimoFilme = new Filme("Filme 7", 1, 100.0);

    @Parameters(name = "{2}")
    public static Collection<Object[]> getParametros() {
        return Arrays.asList(new Object[][] {
                { Arrays.asList(primeiroFilme, segundoFilme), 200.0, "2 Filmes: Sem desconto" },
                { Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme), 275.0, "3 Filmes: Desconto de 25%" },
                { Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme, quartoFilme), 325.0,
                        "4 Filmes: Desconto de 50%" },
                { Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme, quartoFilme, quintoFilme), 350.0,
                        "5 Filmes: Desconto de 75%" },
                { Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme, quartoFilme, quintoFilme,
                        sextoFilme), 350.0, "6 Filmes: Desconto de 100%" },
                { Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme, quartoFilme, quintoFilme,
                        sextoFilme, setimoFilme), 450.0, "7 Filmes: Desconto de 100%" }
        });
    }

    @Test
    public void deveCalcularValorDaLocacaoConsiderandoDescontos() throws FilmeSemEstoqueException, LocadoraException {
        // cenario
        Usuario usuario = new Usuario();

        // acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        // verificacao
        Assert.assertThat(locacao.getValor(), is(equalTo(valorLocacao)));
    }
}