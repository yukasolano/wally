package com.warren.wally.utils;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

@Component
public class Leao {

    @Resource
    private BussinessDaysCalendar bc;

    private double getAliquota(LocalDate start,
                               LocalDate end) {
        long prazo = bc.getDc(start, end);
        if (prazo <= 180) {
            return 22.5 / 100;
        }
        if (prazo <= 360) {
            return 20.0 / 100;
        }
        if (prazo <= 720) {
            return 17.5 / 100;
        }
        return 15.0 / 100;
    }

    public double getIR(double rentabilidadeBruta,
                        LocalDate start,
                        LocalDate end) {
        double aliquota = this.getAliquota(start, end);
        return rentabilidadeBruta * aliquota;
    }
}
