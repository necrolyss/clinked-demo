package lv.dev.clinked.demo.service.stats;

import lv.dev.clinked.demo.util.Period;

public interface StatisticsService {

    Integer articleCountForPeriod(Period queryPeriod);

}
