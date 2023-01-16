package br.ce.wcaquino.suites;

import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.CalculadoraServiceTest;
import br.ce.wcaquino.servicos.CalculoValorLocacaoTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;

// @RunWith(Suite.class)
@SuiteClasses({
        CalculadoraServiceTest.class,
        CalculoValorLocacaoTest.class,
        LocacaoServiceTest.class
})
public class SuiteDeExecucao {

}
