package br.ce.wcaquino.matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;

public class DataComDiferencaDeDiasMatcher extends TypeSafeMatcher<Date> {
    private Integer quantidadeDeDias;

    public DataComDiferencaDeDiasMatcher(Integer quantidadeDeDias) {
        this.quantidadeDeDias = quantidadeDeDias;
    }

    public void describeTo(Description description) {
    }

    @Override
    protected boolean matchesSafely(Date data) {
        return isMesmaData(data, obterDataComDiferencaDias(quantidadeDeDias));
    }
}
