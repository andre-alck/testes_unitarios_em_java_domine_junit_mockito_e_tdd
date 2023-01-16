package br.ce.wcaquino.matchers;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import static br.ce.wcaquino.utils.DataUtils.verificarDiaSemana;;

public class DiaDaSemanaMatcher extends TypeSafeMatcher<Date> {
    private Integer diaDaSemana;

    public DiaDaSemanaMatcher(Integer diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public void describeTo(Description description) {
        Calendar data = Calendar.getInstance();

        data.set(Calendar.DAY_OF_WEEK, diaDaSemana);
        String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
        description.appendText(dataExtenso);
    }

    @Override
    protected boolean matchesSafely(Date item) {
        return verificarDiaSemana(item, diaDaSemana);
    }
}