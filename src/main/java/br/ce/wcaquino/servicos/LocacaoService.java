package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuário está vazio.");
		}

		if (filmes == null || filmes.isEmpty()) {
			throw new LocadoraException("Filme está vazio.");
		}

		for (int i = 0; i < filmes.size(); i++) {
			if (filmes.get(i).getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		double valorTotal = 0;
		for (int i = 0; i < filmes.size(); i++) {
			Double valorDoFilme = filmes.get(i).getPrecoLocacao();

			if (i == 2) {
				valorDoFilme = valorDoFilme * 0.75;
			} else if (i == 3) {
				valorDoFilme = valorDoFilme * 0.50;
			} else if (i == 4) {
				valorDoFilme = valorDoFilme * 0.25;
			} else if (i == 5) {
				valorDoFilme = 0.0;
			}

			valorTotal += valorDoFilme;
		}

		locacao.setValor(valorTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);

		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			locacao.setDataRetorno(DataUtils.adicionarDias(dataEntrega, 1));
		} else {
			locacao.setDataRetorno(dataEntrega);
		}

		// Salvando a locacao...
		// TODO adicionar método para salvar

		return locacao;
	}
}