package lv.dev.clinked.demo.controller.stats;

import lv.dev.clinked.demo.payloads.stats.PublishedArticlesResponse;
import lv.dev.clinked.demo.service.stats.StatisticsService;
import lv.dev.clinked.demo.util.Period;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class StatisticsControllerTest {

    private static final Integer ARTICLES_PER_DAY = 1;
    private static final Integer ARTICLES_PER_WEEK = 5;
    private static final Integer ARTICLES_PER_MONTH = 7;
    private static final Integer ARTICLES_PER_YEAR = 42;

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private StatisticsController statisticsController;

    @Test
    void countsForDefaultPeriod() {
        given(statisticsService.articleCountForPeriod(Period.WEEK)).willReturn(ARTICLES_PER_WEEK);

        ResponseEntity<PublishedArticlesResponse> responseEntity = statisticsController.countForPeriod(null);

        assertThat(responseEntity).isEqualTo(ResponseEntity.ok(new PublishedArticlesResponse(ARTICLES_PER_WEEK)));
    }

    @Test
    void countsForDifferentPeriods() {
        given(statisticsService.articleCountForPeriod(Period.DAY)).willReturn(ARTICLES_PER_DAY);
        given(statisticsService.articleCountForPeriod(Period.WEEK)).willReturn(ARTICLES_PER_WEEK);
        given(statisticsService.articleCountForPeriod(Period.MONTH)).willReturn(ARTICLES_PER_MONTH);
        given(statisticsService.articleCountForPeriod(Period.YEAR)).willReturn(ARTICLES_PER_YEAR);
        ResponseEntity<PublishedArticlesResponse> expectedDailyResponse =
                ResponseEntity.ok(new PublishedArticlesResponse(ARTICLES_PER_DAY));
        ResponseEntity<PublishedArticlesResponse> expectedWeeklyResponse =
                ResponseEntity.ok(new PublishedArticlesResponse(ARTICLES_PER_WEEK));
        ResponseEntity<PublishedArticlesResponse> expectedMonthlyResponse =
                ResponseEntity.ok(new PublishedArticlesResponse(ARTICLES_PER_MONTH));
        ResponseEntity<PublishedArticlesResponse> expectedYearlyResponse =
                ResponseEntity.ok(new PublishedArticlesResponse(ARTICLES_PER_YEAR));

        assertThat(statisticsController.countForPeriod("day")).isEqualTo(expectedDailyResponse);
        assertThat(statisticsController.countForPeriod("week")).isEqualTo(expectedWeeklyResponse);
        assertThat(statisticsController.countForPeriod("month")).isEqualTo(expectedMonthlyResponse);
        assertThat(statisticsController.countForPeriod("year")).isEqualTo(expectedYearlyResponse);
    }

    @Test
    void failsOnIllegalPeriod() {
        given(statisticsService.articleCountForPeriod(any())).willReturn(ARTICLES_PER_YEAR);

        assertThrows(IllegalArgumentException.class, () -> statisticsController.countForPeriod("foo"));
    }
}