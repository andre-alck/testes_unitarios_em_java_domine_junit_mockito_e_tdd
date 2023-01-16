package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.builders.FilmeBuilder.umFilme;
import static br.ce.wcaquino.builders.UsuarioBuilder.umUsuario;
import static br.ce.wcaquino.builders.LocacaoBuilder.umLocacao;
import static br.ce.wcaquino.matchers.MatchersProntos.caiEmUmaSegunda;
import static br.ce.wcaquino.matchers.MatchersProntos.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProntos.ehHojeComDiferencaDeDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static br.ce.wcaquino.utils.DataUtils.verificarDiaSemana;
import static br.ce.wcaquino.utils.DataUtils.isMesmaData;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assume;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import br.ce.wcaquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

import buildermaster.BuilderMaster;

@FixMethodOrder
public class LocacaoServiceTest {
	@Rule
	public ErrorCollector errorCollector = new ErrorCollector();

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private LocacaoService locacaoService;
	private LocacaoDao locacaoDao;
	private SpcService spcService;
	private EmailService emailService;

	@Before
	public void setup() {
		locacaoService = new LocacaoService();

		locacaoDao = mock(LocacaoDao.class);
		locacaoService.setLocacaoDao(locacaoDao);

		spcService = mock(SpcService.class);
		locacaoService.setSpcService(spcService);

		emailService = mock(EmailService.class);
		locacaoService.setEmailService(emailService);
	}

	@Test
	public void deveAlugarFilmeComSucesso() throws Exception {
		Assume.assumeFalse(verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> lFilme = Arrays.asList(umFilme().agora());

		// acao
		Locacao locacao = locacaoService.alugarFilme(usuario, lFilme);

		// verificacao
		errorCollector.checkThat(locacao.getValor(), is(equalTo(5.0)));
		errorCollector.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()),
				is(equalTo(true)));
		errorCollector.checkThat(locacao.getDataLocacao(), ehHoje());
		errorCollector.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDeDias(1));
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void deveLancarExcessaoAoAlugarFilmeSemEstoqueElegante() throws Exception {
		Usuario usuario = umUsuario().agora();
		List<Filme> lFilme = Arrays.asList(umFilme().semEstoque().agora());

		locacaoService.alugarFilme(usuario, lFilme);
	}

	@Test
	public void deveLancarExcessaoAoAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		List<Filme> lFilme = Arrays.asList(umFilme().agora());

		try {
			locacaoService.alugarFilme(null, lFilme);
			fail();
		} catch (LocadoraException locadoraException) {
			assertThat(locadoraException.getMessage(),
					is(equalTo("Usuário está vazio.")));
		}
	}

	@Test
	public void deveLancarExcessaoAoAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		Usuario usuario = umUsuario().agora();

		expectedException.expect(LocadoraException.class);
		expectedException.expectMessage("Filme está vazio.");

		locacaoService.alugarFilme(usuario, null);
	}

	@Test
	public void deveDevolverFilmeNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(verificarDiaSemana(new Date(), Calendar.SATURDAY));

		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		// acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		// verificacao
		assertThat(locacao.getDataRetorno(), caiEmUmaSegunda());
	}

	@Test
	public void naoDeveAlugarFilmeCasoUsuarioEstejaNegativado() throws LocadoraException, FilmeSemEstoqueException {
		// cenario
		Usuario usuario = umUsuario().agora();
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		when(spcService.possuiNegativacao(usuario)).thenReturn(true);

		expectedException.expect(LocadoraException.class);
		expectedException.expectMessage("Usuário está negativado.");

		// acao
		locacaoService.alugarFilme(usuario, filmes);
	}

	@Test
	public void deveEnviarEmailParaLocacoesAtrasadas() {
		// cenario
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuário 2").agora();

		List<Locacao> locacoes = Arrays.asList(
				umLocacao().comUsuario(usuario).comDataRetorno(obterDataComDiferencaDias(-2)).agora());

		when(locacaoDao.obterLocacoesPendentes()).thenReturn(locacoes);

		// acao
		emailService.notificarAtraso(null);

		// verificacao
		verify(emailService).notificarAtraso(usuario);
	}

	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}
}
