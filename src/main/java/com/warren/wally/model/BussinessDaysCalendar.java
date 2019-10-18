package com.warren.wally.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.warren.wally.utils.DateUtils.dateOf;

public class BussinessDaysCalendar {

    List<LocalDate> holidays = new ArrayList<LocalDate>();

    public long getDu(LocalDate start, LocalDate end) {
        long count = 0;
        if (start.isAfter(end)) {
            return -1;
        }
        if (start.isEqual(end)) {
            return 0;
        }

        LocalDate iterator = start.plusDays(1);
        while (iterator.isBefore(end) || iterator.isEqual(end)) {
            if (isWorkDay(iterator)) {
                count++;
            }
            iterator = iterator.plusDays(1);
        }

        return count;
    }

    public long getDc(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    public LocalDate getNextWorkDay(LocalDate date) {
        if (isWorkDay(date)) {
            return date;
        }
        LocalDate iterator = date.plusDays(1);
        while (!isWorkDay(iterator)) {
            iterator = iterator.plusDays(1);
        }
        return iterator;
    }

    private boolean isWorkDay(LocalDate date) {
        return !isWeekEnd(date) && !isHoliday(date);
    }

    private boolean isWeekEnd(LocalDate date) {
        DayOfWeek dow = date.getDayOfWeek();
        return dow == DayOfWeek.SATURDAY || dow == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(LocalDate date) {
        return holidays.contains(date);
    }

    public BussinessDaysCalendar() {
        holidays.add(dateOf("01/01/2015"));
        holidays.add(dateOf("16/02/2015"));
        holidays.add(dateOf("17/02/2015"));
        holidays.add(dateOf("03/04/2015"));
        holidays.add(dateOf("21/04/2015"));
        holidays.add(dateOf("01/05/2015"));
        holidays.add(dateOf("04/06/2015"));
        holidays.add(dateOf("07/09/2015"));
        holidays.add(dateOf("12/10/2015"));
        holidays.add(dateOf("02/11/2015"));
        holidays.add(dateOf("15/11/2015"));
        holidays.add(dateOf("25/12/2015"));
        holidays.add(dateOf("01/01/2016"));
        holidays.add(dateOf("08/02/2016"));
        holidays.add(dateOf("09/02/2016"));
        holidays.add(dateOf("25/03/2016"));
        holidays.add(dateOf("21/04/2016"));
        holidays.add(dateOf("01/05/2016"));
        holidays.add(dateOf("26/05/2016"));
        holidays.add(dateOf("07/09/2016"));
        holidays.add(dateOf("12/10/2016"));
        holidays.add(dateOf("02/11/2016"));
        holidays.add(dateOf("15/11/2016"));
        holidays.add(dateOf("25/12/2016"));
        holidays.add(dateOf("01/01/2017"));
        holidays.add(dateOf("27/02/2017"));
        holidays.add(dateOf("28/02/2017"));
        holidays.add(dateOf("14/04/2017"));
        holidays.add(dateOf("21/04/2017"));
        holidays.add(dateOf("01/05/2017"));
        holidays.add(dateOf("15/06/2017"));
        holidays.add(dateOf("07/09/2017"));
        holidays.add(dateOf("12/10/2017"));
        holidays.add(dateOf("02/11/2017"));
        holidays.add(dateOf("15/11/2017"));
        holidays.add(dateOf("25/12/2017"));
        holidays.add(dateOf("01/01/2018"));
        holidays.add(dateOf("12/02/2018"));
        holidays.add(dateOf("13/02/2018"));
        holidays.add(dateOf("30/03/2018"));
        holidays.add(dateOf("21/04/2018"));
        holidays.add(dateOf("01/05/2018"));
        holidays.add(dateOf("31/05/2018"));
        holidays.add(dateOf("07/09/2018"));
        holidays.add(dateOf("12/10/2018"));
        holidays.add(dateOf("02/11/2018"));
        holidays.add(dateOf("15/11/2018"));
        holidays.add(dateOf("25/12/2018"));
        holidays.add(dateOf("01/01/2019"));
        holidays.add(dateOf("04/03/2019"));
        holidays.add(dateOf("05/03/2019"));
        holidays.add(dateOf("19/04/2019"));
        holidays.add(dateOf("21/04/2019"));
        holidays.add(dateOf("01/05/2019"));
        holidays.add(dateOf("20/06/2019"));
        holidays.add(dateOf("07/09/2019"));
        holidays.add(dateOf("12/10/2019"));
        holidays.add(dateOf("02/11/2019"));
        holidays.add(dateOf("15/11/2019"));
        holidays.add(dateOf("25/12/2019"));
        holidays.add(dateOf("01/01/2020"));
        holidays.add(dateOf("24/02/2020"));
        holidays.add(dateOf("25/02/2020"));
        holidays.add(dateOf("10/04/2020"));
        holidays.add(dateOf("21/04/2020"));
        holidays.add(dateOf("01/05/2020"));
        holidays.add(dateOf("11/06/2020"));
        holidays.add(dateOf("07/09/2020"));
        holidays.add(dateOf("12/10/2020"));
        holidays.add(dateOf("02/11/2020"));
        holidays.add(dateOf("15/11/2020"));
        holidays.add(dateOf("25/12/2020"));
        holidays.add(dateOf("01/01/2021"));
        holidays.add(dateOf("15/02/2021"));
        holidays.add(dateOf("16/02/2021"));
        holidays.add(dateOf("02/04/2021"));
        holidays.add(dateOf("21/04/2021"));
        holidays.add(dateOf("01/05/2021"));
        holidays.add(dateOf("03/06/2021"));
        holidays.add(dateOf("07/09/2021"));
        holidays.add(dateOf("12/10/2021"));
        holidays.add(dateOf("02/11/2021"));
        holidays.add(dateOf("15/11/2021"));
        holidays.add(dateOf("25/12/2021"));
        holidays.add(dateOf("01/01/2022"));
        holidays.add(dateOf("28/02/2022"));
        holidays.add(dateOf("01/03/2022"));
        holidays.add(dateOf("15/04/2022"));
        holidays.add(dateOf("21/04/2022"));
        holidays.add(dateOf("01/05/2022"));
        holidays.add(dateOf("16/06/2022"));
        holidays.add(dateOf("07/09/2022"));
        holidays.add(dateOf("12/10/2022"));
        holidays.add(dateOf("02/11/2022"));
        holidays.add(dateOf("15/11/2022"));
        holidays.add(dateOf("25/12/2022"));
        holidays.add(dateOf("01/01/2023"));
        holidays.add(dateOf("20/02/2023"));
        holidays.add(dateOf("21/02/2023"));
        holidays.add(dateOf("07/04/2023"));
        holidays.add(dateOf("21/04/2023"));
        holidays.add(dateOf("01/05/2023"));
        holidays.add(dateOf("08/06/2023"));
        holidays.add(dateOf("07/09/2023"));
        holidays.add(dateOf("12/10/2023"));
        holidays.add(dateOf("02/11/2023"));
        holidays.add(dateOf("15/11/2023"));
        holidays.add(dateOf("25/12/2023"));
        holidays.add(dateOf("01/01/2024"));
        holidays.add(dateOf("12/02/2024"));
        holidays.add(dateOf("13/02/2024"));
        holidays.add(dateOf("29/03/2024"));
        holidays.add(dateOf("21/04/2024"));
        holidays.add(dateOf("01/05/2024"));
        holidays.add(dateOf("30/05/2024"));
        holidays.add(dateOf("07/09/2024"));
        holidays.add(dateOf("12/10/2024"));
        holidays.add(dateOf("02/11/2024"));
        holidays.add(dateOf("15/11/2024"));
        holidays.add(dateOf("25/12/2024"));
        holidays.add(dateOf("01/01/2025"));
        holidays.add(dateOf("03/03/2025"));
        holidays.add(dateOf("04/03/2025"));
        holidays.add(dateOf("18/04/2025"));
        holidays.add(dateOf("21/04/2025"));
        holidays.add(dateOf("01/05/2025"));
        holidays.add(dateOf("19/06/2025"));
        holidays.add(dateOf("07/09/2025"));
        holidays.add(dateOf("12/10/2025"));
        holidays.add(dateOf("02/11/2025"));
        holidays.add(dateOf("15/11/2025"));
        holidays.add(dateOf("25/12/2025"));
        holidays.add(dateOf("01/01/2026"));
        holidays.add(dateOf("16/02/2026"));
        holidays.add(dateOf("17/02/2026"));
        holidays.add(dateOf("03/04/2026"));
        holidays.add(dateOf("21/04/2026"));
        holidays.add(dateOf("01/05/2026"));
        holidays.add(dateOf("04/06/2026"));
        holidays.add(dateOf("07/09/2026"));
        holidays.add(dateOf("12/10/2026"));
        holidays.add(dateOf("02/11/2026"));
        holidays.add(dateOf("15/11/2026"));
        holidays.add(dateOf("25/12/2026"));
        holidays.add(dateOf("01/01/2027"));
        holidays.add(dateOf("08/02/2027"));
        holidays.add(dateOf("09/02/2027"));
        holidays.add(dateOf("26/03/2027"));
        holidays.add(dateOf("21/04/2027"));
        holidays.add(dateOf("01/05/2027"));
        holidays.add(dateOf("27/05/2027"));
        holidays.add(dateOf("07/09/2027"));
        holidays.add(dateOf("12/10/2027"));
        holidays.add(dateOf("02/11/2027"));
        holidays.add(dateOf("15/11/2027"));
        holidays.add(dateOf("25/12/2027"));
    }

}
