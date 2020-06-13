package com.warren.wally.model.calculadora;

import com.warren.wally.utils.BussinessDaysCalendar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

@Component
public class CalculadoraPreStrategy implements Calculadora {

    @Resource
    private BussinessDaysCalendar bc;

    @Override
    public TipoRentabilidade getTipoRentabilidade() {
        return TipoRentabilidade.PRE;
    }

    @Override
    public double calculaVPBruto(double valorAplicado,
                                 double taxa,
                                 LocalDate dtAplicacao,
                                 LocalDate dtRef) {
        long du = bc.getDu(dtAplicacao, dtRef);
        double fte = Math.pow(1 + taxa, du / 252.0);
        return valorAplicado * fte;
    }

}
