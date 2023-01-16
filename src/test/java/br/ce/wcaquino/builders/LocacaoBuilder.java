package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Usuario;
import java.util.Arrays;
import java.lang.Double;
import java.util.Date;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;

public class LocacaoBuilder {
    private Locacao elemento;

    private LocacaoBuilder() {
    }

    public static LocacaoBuilder umLocacao() {
        LocacaoBuilder builder = new LocacaoBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(LocacaoBuilder builder) {
        builder.elemento = new Locacao();
        Locacao elemento = builder.elemento;

        elemento.setUsuario(null);
        elemento.setFilme(null);
        elemento.setDataLocacao(null);
        elemento.setDataRetorno(null);
        elemento.setValor(0.0);
    }

    public LocacaoBuilder comUsuario(Usuario param) {
        elemento.setUsuario(param);
        return this;
    }

    public LocacaoBuilder comListaFilme(Filme... params) {
        elemento.setFilme(Arrays.asList(params));
        return this;
    }

    public LocacaoBuilder comDataLocacao(Date param) {
        elemento.setDataLocacao(param);
        return this;
    }

    public LocacaoBuilder comDataRetorno(Date param) {
        elemento.setDataRetorno(param);
        return this;
    }

    public LocacaoBuilder comValor(Double param) {
        elemento.setValor(param);
        return this;
    }

    public Locacao agora() {
        return elemento;
    }
}