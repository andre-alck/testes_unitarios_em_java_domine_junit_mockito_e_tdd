package br.ce.wcaquino.servicos;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.wcaquino.entidades.Calculadora;
import br.ce.wcaquino.exceptions.NaoEPossivelDividirPor0Exception;

public class CalculadoraServiceTest {

    Calculadora calculadora;

    @Before
    public void setup() {
        calculadora = new Calculadora();
    }

    @Test
    public void deveSomarDoisValores() {
        int primeiroValor = 1;
        int segundoValor = 2;
        int resultadoDaSomaManual = primeiroValor + segundoValor;

        int resultadoDaSomaPelaFuncao = calculadora.somarDoisValores(primeiroValor, segundoValor);

        Assert.assertThat(resultadoDaSomaPelaFuncao, CoreMatchers.is(CoreMatchers.equalTo(resultadoDaSomaManual)));
    }

    @Test
    public void deveSubtrairDoisValores() {
        int primeiroValor = 1;
        int segundoValor = 2;
        int resultadoDaSubtracaoManual = primeiroValor - segundoValor;

        int resultadoDaSubtracaoPelaFuncao = calculadora.subtrairDoisValores(primeiroValor, segundoValor);

        Assert.assertThat(resultadoDaSubtracaoPelaFuncao,
                CoreMatchers.is(CoreMatchers.equalTo(resultadoDaSubtracaoManual)));
    }

    @Test
    public void deveDividirDoisValores() throws NaoEPossivelDividirPor0Exception {
        int primeiroValor = 1;
        int segundoValor = 2;
        int resultadoDaDivisaoManual = primeiroValor / segundoValor;

        int resultadoDaSubtracaoPelaFuncao = calculadora.dividirDoisValores(primeiroValor, segundoValor);

        Assert.assertThat(resultadoDaSubtracaoPelaFuncao,
                CoreMatchers.is(CoreMatchers.equalTo(resultadoDaDivisaoManual)));
    }

    @Test(expected = NaoEPossivelDividirPor0Exception.class)
    public void deveLancarExcessoAoDividirPor0() throws NaoEPossivelDividirPor0Exception {
        int primeiroValor = 1;
        int segundoValor = 0;

        calculadora.dividirDoisValores(primeiroValor, segundoValor);
    }
}
