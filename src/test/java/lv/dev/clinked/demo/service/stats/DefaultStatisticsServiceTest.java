package lv.dev.clinked.demo.service.stats;

import lv.dev.clinked.demo.repository.article.ArticleRepository;
import lv.dev.clinked.demo.util.Period;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class DefaultStatisticsServiceTest {

    @Mock
    private ArticleRepository articleRepository;
    @Captor
    private ArgumentCaptor<Date> dateFromCaptor;
    @Captor
    private ArgumentCaptor<Date> dateToCaptor;

    @InjectMocks
    private DefaultStatisticsService defaultStatisticsService;

    @Test
    void countsArticlesForPeriod() {
        defaultStatisticsService.articleCountForPeriod(Period.WEEK);

        verify(articleRepository).countArticlesByPublishDateAfterAndPublishDateBefore(dateFromCaptor.capture(), dateToCaptor.capture());
        assertThat(DateUtils.isSameDay(dateFromCaptor.getValue(), DateUtils.addDays(new Date(), -7))).isTrue();
        assertThat(DateUtils.isSameDay(dateToCaptor.getValue(), new Date())).isTrue();

    }
}