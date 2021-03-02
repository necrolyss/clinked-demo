package lv.dev.clinked.demo.util.format;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("DF_DEFAULT")
class DefaultDateFormatter implements DateFormatter {

    private static final SimpleDateFormat DEFAULT_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public String formatDate(Date input) {
        return DEFAULT_FORMAT.format(input);
    }

}
