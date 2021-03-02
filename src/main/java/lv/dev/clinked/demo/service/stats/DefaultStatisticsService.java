package lv.dev.clinked.demo.service.stats;

import lv.dev.clinked.demo.repository.article.ArticleRepository;
import lv.dev.clinked.demo.util.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
class DefaultStatisticsService implements StatisticsService {

    private final ArticleRepository articleRepository;

    @Autowired
    DefaultStatisticsService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Integer articleCountForPeriod(Period queryPeriod) {
        Date currentDate = new Date();
        Date countFromDate = queryPeriod.subtractFrom(currentDate);
        return articleCountForPeriod(countFromDate, currentDate);
    }

    private Integer articleCountForPeriod(Date dateFrom, Date dateTo) {
        return articleRepository.countArticlesByPublishDateAfterAndPublishDateBefore(dateFrom, dateTo);
    }

}
