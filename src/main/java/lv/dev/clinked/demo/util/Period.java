package lv.dev.clinked.demo.util;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public enum Period {

    DAY {
        @Override
        public Date subtractFrom(Date input) {
            return DateUtils.addDays(input, -1);
        }
    }, WEEK {
        @Override
        public Date subtractFrom(Date input) {
            return DateUtils.addDays(input, -7);
        }
    }, MONTH {
        @Override
        public Date subtractFrom(Date input) {
            return DateUtils.addMonths(input, -1);
        }
    }, YEAR {
        @Override
        public Date subtractFrom(Date input) {
            return DateUtils.addYears(input, -1);
        }
    };

    public abstract Date subtractFrom(Date input);

}
