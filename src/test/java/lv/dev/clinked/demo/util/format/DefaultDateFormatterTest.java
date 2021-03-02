package lv.dev.clinked.demo.util.format;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
class DefaultDateFormatterTest {

    @InjectMocks
    private DefaultDateFormatter defaultDateFormatter;

    @Test
    void formatsDate() {
        Date date = prepareDate();

        assertThat(defaultDateFormatter.formatDate(date)).isEqualTo("01/03/2021");
    }

    private Date prepareDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2021);
        calendar.set(Calendar.MONTH, 2);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }
}