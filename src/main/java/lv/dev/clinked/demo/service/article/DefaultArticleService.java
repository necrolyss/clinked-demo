package lv.dev.clinked.demo.service.article;

import lv.dev.clinked.demo.model.article.Article;
import lv.dev.clinked.demo.payloads.article.ArticleSummary;
import lv.dev.clinked.demo.repository.article.ArticleRepository;
import lv.dev.clinked.demo.util.format.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
class DefaultArticleService implements ArticleService {

    private final ArticleRepository articleRepository;
    private final DateFormatter dateFormatter;

    @Autowired
    DefaultArticleService(ArticleRepository articleRepository,
                          Map<String, DateFormatter> publishDateFormatters,
                          @Value("${app.date.format:DF_DEFAULT}") String dateFormatId) {
        this.articleRepository = articleRepository;
        this.dateFormatter = publishDateFormatters.get(dateFormatId);
    }

    @Override
    public void persist(Article article) {
        articleRepository.save(article);
    }

    @Override
    public Page<ArticleSummary> articleSummariesOf(Pageable pageRequest) {
        return articleRepository
                .findAll(pageRequest)
                .map(this::getArticleSummaryOf);
    }

    private ArticleSummary getArticleSummaryOf(Article article) {
        return new ArticleSummary(
                article.getTitle(),
                article.getAuthor().getName(),
                formatPublishDate(article)
        );
    }

    private String formatPublishDate(Article article) {
        return dateFormatter.formatDate(article.getPublishDate());
    }
}
