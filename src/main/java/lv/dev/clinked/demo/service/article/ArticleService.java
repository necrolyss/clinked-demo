package lv.dev.clinked.demo.service.article;

import lv.dev.clinked.demo.model.article.Article;
import lv.dev.clinked.demo.payloads.article.ArticleSummary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    void persist(Article article);

    Page<ArticleSummary> articleSummariesOf(Pageable pageRequest);
}
