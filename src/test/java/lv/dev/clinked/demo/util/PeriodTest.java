package lv.dev.clinked.demo.util;

import org.apache.commons.lang3.time.DateUtils;
import org.assertj.core.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class PeriodTest {

    @Test
    void periodSubtractionDay() {
        Date currentTime = new Date();

        Date previousDay = Period.DAY.subtractFrom(currentTime);

        long timeDiff = DateUtil.timeDifference(previousDay, currentTime);
        assertThat(timeDiff).isEqualTo(DateUtils.MILLIS_PER_DAY);
    }

    @Test
    void periodSubtractionWeek() {
        Date currentTime = new Date();

        Date previousWeek = Period.WEEK.subtractFrom(currentTime);

        long timeDiff = DateUtil.timeDifference(previousWeek, currentTime);
        assertThat(timeDiff).isEqualTo(DateUtils.MILLIS_PER_DAY * 7);
    }

    @Test
    void periodSubtractionMonth() {
        Date currentTime = new Date();
        int currentMonthOrdinal = DateUtil.monthOf(currentTime);

        Date previousMonth = Period.MONTH.subtractFrom(currentTime);

        int monthDiff = Math.abs(currentMonthOrdinal - DateUtil.monthOf(previousMonth));
        assertThat(monthDiff == 1 || monthDiff == 11).isTrue();
    }

    @Test
    void periodSubtractionYear() {
        Date currentTime = new Date();

        Date previousYear = Period.YEAR.subtractFrom(currentTime);

        assertThat(DateUtil.yearOf(previousYear)).isEqualTo(DateUtil.yearOf(currentTime) - 1);
    }

}