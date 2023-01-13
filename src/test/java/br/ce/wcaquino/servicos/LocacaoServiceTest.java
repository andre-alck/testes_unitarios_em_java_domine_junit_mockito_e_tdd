package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

@FixMethodOrder
public class LocacaoServiceTest {
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private LocacaoService locacaoService;

	@Before
	public void setup() {
		locacaoService = new LocacaoService();
	}

	@Test
	public void deveAlugarFilmeComSucesso() {
		// cenario
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 2, 5.0);
		List<Filme> lFilme = Arrays.asList(filme);

		// acao
		Locacao locacao;
		try {
			locacao = locacaoService.alugarFilme(usuario, lFilme);

			// verificacao
			errorCollector.checkThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(5.0)));
			errorCollector.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),
					CoreMatchers.is(CoreMatchers.equalTo(true)));
			errorCollector.checkThat(
					DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),
					CoreMatchers.is(CoreMatchers.equalTo(true)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcessaoAoAlugarFilmeSemEstoqueElegante() throws Exception {
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);
		List<Filme> lFilme = Arrays.asList(filme);

		locacaoService.alugarFilme(usuario, lFilme);
	}

	@Test
	public void deveLancarExcessaoAoAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		Filme filme = new Filme("Filme 1", 1, 5.0);
		List<Filme> lFilme = Arrays.asList(filme);

		try {
			locacaoService.alugarFilme(null, lFilme);
			Assert.fail();
		} catch (LocadoraException locadoraException) {
			Assert.assertThat(locadoraException.getMessage(),
					CoreMatchers.is(CoreMatchers.equalTo("Usuário está vazio.")));
		}
	}

	@Test
	public void deveLancarExcessaoAoAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = new Usuario();

		expectedException.expect(LocadoraException.class);
		expectedException.expectMessage("Filme está vazio.");

		locacaoService.alugarFilme(usuario, null);
	}

	@Test
	public void devePagar75PorCentoNoTerceiroLivroAoAlugar3Filmes() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario();
		Filme primeiroFilme = new Filme("Filme 1", 1, 100.0);
		Filme segundoFilme = new Filme("Filme 2", 1, 100.0);
		Filme terceiroFilme = new Filme("Filme 3", 1, 100.0);
		List<Filme> filmes = Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme);

		// acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		// verificacao
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(275.0)));
	}

	@Test
	public void devePagar50PorCentoNoQuartoLivroAoAlugar4Filmes() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario();
		Filme primeiroFilme = new Filme("Filme 1", 1, 100.0);
		Filme segundoFilme = new Filme("Filme 2", 1, 100.0);
		Filme terceiroFilme = new Filme("Filme 3", 1, 100.0);
		Filme quartoFilme = new Filme("Filme 4", 1, 100.0);
		List<Filme> filmes = Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme, quartoFilme);

		// acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		// verificacao
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(325.0)));
	}

	@Test
	public void devePagar25PorCentoNoQuintoLivroAoAlugar5Filmes() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario();
		Filme primeiroFilme = new Filme("Filme 1", 1, 100.0);
		Filme segundoFilme = new Filme("Filme 2", 1, 100.0);
		Filme terceiroFilme = new Filme("Filme 3", 1, 100.0);
		Filme quartoFilme = new Filme("Filme 4", 1, 100.0);
		Filme quintoFilme = new Filme("Filme 5", 1, 100.0);
		List<Filme> filmes = Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme, quartoFilme, quintoFilme);

		// acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		// verificacao
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(350.0)));
	}

	@Test
	public void devePagar0PorCentoNSextoLivroAoAlugar6Filmes() throws FilmeSemEstoqueException, LocadoraException {
		// cenario
		LocacaoService locacaoService = new LocacaoService();
		Usuario usuario = new Usuario();
		Filme primeiroFilme = new Filme("Filme 1", 1, 100.0);
		Filme segundoFilme = new Filme("Filme 2", 1, 100.0);
		Filme terceiroFilme = new Filme("Filme 3", 1, 100.0);
		Filme quartoFilme = new Filme("Filme 4", 1, 100.0);
		Filme quintoFilme = new Filme("Filme 5", 1, 100.0);
		Filme sextoFilme = new Filme("Filme 6", 1, 100.0);
		List<Filme> filmes = Arrays.asList(primeiroFilme, segundoFilme, terceiroFilme, quartoFilme, quintoFilme,
				sextoFilme);

		// acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		// verificacao
		Assert.assertThat(locacao.getValor(), CoreMatchers.is(CoreMatchers.equalTo(350.0)));
	}

	@Test
	public void deveDevolverFilmeNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));

		Usuario usuario = new Usuario("Usuário 1");
		Filme filme = new Filme("Filme 1", 1, 100.0);
		List<Filme> filmes = Arrays.asList(filme);

		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		boolean ehSegunda = DataUtils.verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);

		Assert.assertTrue(ehSegunda);
	}
}
