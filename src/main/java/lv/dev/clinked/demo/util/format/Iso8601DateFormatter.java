package lv.dev.clinked.demo.util.format;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component("DF_ISO_8601")
class Iso8601DateFormatter implements DateFormatter {

    private static final SimpleDateFormat ISO_8601_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String formatDate(Date input) {
        return ISO_8601_FORMAT.format(input);
    }

}
