package lv.dev.clinked.demo.controller.stats;

import lv.dev.clinked.demo.service.stats.StatisticsService;
import lv.dev.clinked.demo.util.Period;

import lv.dev.clinked.demo.payloads.stats.PublishedArticlesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/stats")
public class StatisticsController {

    private static final Period DEFAULT_ARTICLE_COUNT_PERIOD = Period.WEEK;

    private final StatisticsService statisticsService;

    @Autowired
    StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/articles/count")
    ResponseEntity<PublishedArticlesResponse> countForPeriod(
            @RequestParam(value = "period", required = false) String period) {
        Integer articleCount = statisticsService.articleCountForPeriod(queryPeriodOf(period));
        return ResponseEntity.ok(new PublishedArticlesResponse(articleCount));
    }

    private Period queryPeriodOf(String period) {
        return Optional.ofNullable(period)
                .map(value -> Period.valueOf(value.toUpperCase()))
                .orElse(DEFAULT_ARTICLE_COUNT_PERIOD);
    }

}
