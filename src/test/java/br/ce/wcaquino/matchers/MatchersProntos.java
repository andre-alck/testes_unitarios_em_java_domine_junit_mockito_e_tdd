package br.ce.wcaquino.matchers;

import java.util.Calendar;

public class MatchersProntos {
    public static DiaDaSemanaMatcher caiEm(Integer diaDaSemana) {
        return new DiaDaSemanaMatcher(diaDaSemana);
    }

    public static DiaDaSemanaMatcher caiEmUmaSegunda() {
        return new DiaDaSemanaMatcher(Calendar.MONDAY);
    }

    public static DataComDiferencaDeDiasMatcher ehHojeComDiferencaDeDias(Integer quantidadeDeDias) {
        return new DataComDiferencaDeDiasMatcher(quantidadeDeDias);
    }

    public static DataComDiferencaDeDiasMatcher ehHoje() {
        return new DataComDiferencaDeDiasMatcher(0);
    }
}
