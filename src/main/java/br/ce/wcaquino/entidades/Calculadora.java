package br.ce.wcaquino.entidades;

import br.ce.wcaquino.exceptions.NaoEPossivelDividirPor0Exception;

public class Calculadora {

    public int somarDoisValores(int primeiroValor, int segundoValor) {
        int resultadoDaSoma = primeiroValor + segundoValor;
        return resultadoDaSoma;
    }

    public int subtrairDoisValores(int primeiroValor, int segundoValor) {
        return primeiroValor - segundoValor;
    }

    public int dividirDoisValores(int primeiroValor, int segundoValor) throws NaoEPossivelDividirPor0Exception {
        if (segundoValor == 0) {
            throw new NaoEPossivelDividirPor0Exception();
        }
        return primeiroValor / segundoValor;
    }

}
