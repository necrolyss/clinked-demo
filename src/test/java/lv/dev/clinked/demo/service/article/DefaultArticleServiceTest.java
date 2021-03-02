package lv.dev.clinked.demo.service.article;

import lv.dev.clinked.demo.model.article.Article;
import lv.dev.clinked.demo.model.user.User;
import lv.dev.clinked.demo.payloads.article.ArticleSummary;
import lv.dev.clinked.demo.repository.article.ArticleRepository;
import lv.dev.clinked.demo.util.format.DateFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class DefaultArticleServiceTest {

    public static final String ARTICLE_TITLE = "Article title";
    public static final String AUTHOR_NAME = "Chukcha Pisatelj";
    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private Map<String, DateFormatter> publishDateFormatters;
    @Mock
    private Article article;
    @Mock
    private User author;
    @Mock
    private DateFormatter dateFormatter;

    private DefaultArticleService defaultArticleService;

    @BeforeEach
    void setUp() {
        given(publishDateFormatters.get(anyString())).willReturn(dateFormatter);
        defaultArticleService = new DefaultArticleService(
                articleRepository, publishDateFormatters, "DF_ISO_8601"
        );
    }

    @Test
    void persist() {
        defaultArticleService.persist(article);

        verify(articleRepository).save(article);
    }

    @Test
    void articleSummariesOf() {
        mockArticle();
        String expectedPublishDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        PageRequest pageRequest = PageRequest.of(0, 5);
        PageImpl<Article> preparedPage = new PageImpl<>(Collections.singletonList(this.article), pageRequest, 1);
        given(articleRepository.findAll(pageRequest)).willReturn(preparedPage);
        given(dateFormatter.formatDate(any())).willReturn(expectedPublishDate);

        Page<ArticleSummary> articleSummaries = defaultArticleService.articleSummariesOf(pageRequest);

        assertThat(articleSummaries).first()
                .returns(ARTICLE_TITLE, ArticleSummary::title)
                .returns(AUTHOR_NAME, ArticleSummary::authorName)
                .returns(expectedPublishDate, ArticleSummary::publishDate);
    }

    private void mockArticle() {
        given(article.getTitle()).willReturn(ARTICLE_TITLE);
        given(article.getAuthor()).willReturn(author);
        given(author.getName()).willReturn(AUTHOR_NAME);
        given(article.getPublishDate()).willReturn(new Date());
    }
}