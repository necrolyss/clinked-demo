package lv.dev.clinked.demo.controller.article;

import lv.dev.clinked.demo.model.article.Article;
import lv.dev.clinked.demo.model.user.User;
import lv.dev.clinked.demo.payloads.ApiResponse;
import lv.dev.clinked.demo.payloads.PagedResponse;
import lv.dev.clinked.demo.payloads.article.ArticleSummary;
import lv.dev.clinked.demo.payloads.article.CreateArticleRequest;
import lv.dev.clinked.demo.service.article.ArticleService;
import lv.dev.clinked.demo.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class ArticleControllerTest {

    private static final String ARTICLE_TITLE = "Some title";
    private static final Long AUTHOR_ID = 1L;
    private static final String ARTICLE_CONTENT = "Lorem Ipsum";
    private static final Integer MAX_PAGE_SIZE = 5;
    public static final int EXPECTED_TOTAL_PAGES = 2;
    public static final long EXPECTED_TOTAL_ELEMENT = 9L;

    @Mock
    private ArticleService articleService;
    @Mock
    private UserService userService;
    @Mock
    private User author;
    @Captor
    private ArgumentCaptor<Article> articleArgumentCaptor;
    @Mock
    private Page<ArticleSummary> articlePage;

    private ArticleController articleController;

    @BeforeEach
    void setUp() {
        AbstractResourceBasedMessageSource bundleMessageSource =
                new ReloadableResourceBundleMessageSource();
        bundleMessageSource.setUseCodeAsDefaultMessage(true);
        articleController = new ArticleController(
                articleService, userService, bundleMessageSource, MAX_PAGE_SIZE
        );
    }

    @Test
    void createsArticle() {
        Long publishDate = System.currentTimeMillis();
        CreateArticleRequest createArticleRequest = prepareArticle(publishDate);
        given(author.getId()).willReturn(AUTHOR_ID);
        given(userService.userById(AUTHOR_ID)).willReturn(author);
        ApiResponse expectedResponse = new ApiResponse(true, "article.create.success");

        ResponseEntity<ApiResponse> response = articleController.createArticle(createArticleRequest);

        assertThat(response).isEqualTo(ResponseEntity.ok(expectedResponse));
        verify(articleService).persist(articleArgumentCaptor.capture());
        assertThat(articleArgumentCaptor.getValue())
                .returns(ARTICLE_TITLE, Article::getTitle)
                .returns(ARTICLE_CONTENT, Article::getContent)
                .returns(new Date(publishDate), Article::getPublishDate)
                .returns(AUTHOR_ID, article -> article.getAuthor().getId());
    }

    @Test
    void listArticles() {
        List<ArticleSummary> expectedContent = Collections.singletonList(
                new ArticleSummary(ARTICLE_TITLE, "Stephen King", "1974-04-19")
        );
        given(articlePage.getTotalPages()).willReturn(EXPECTED_TOTAL_PAGES);
        given(articlePage.getTotalElements()).willReturn(EXPECTED_TOTAL_ELEMENT);
        given(articlePage.getSize()).willReturn(MAX_PAGE_SIZE);
        given(articlePage.getContent()).willReturn(expectedContent);
        given(articlePage.isLast()).willReturn(Boolean.TRUE);
        given(articleService.articleSummariesOf(PageRequest.of(0, MAX_PAGE_SIZE))).willReturn(articlePage);

        PagedResponse<ArticleSummary> pagedResponse = articleController.listArticles(0, MAX_PAGE_SIZE + 1);

        assertThat(pagedResponse)
                .returns(0, PagedResponse::getPage)
                .returns(EXPECTED_TOTAL_ELEMENT, PagedResponse::getTotalElements)
                .returns(MAX_PAGE_SIZE, PagedResponse::getSize)
                .returns(EXPECTED_TOTAL_PAGES, PagedResponse::getTotalPages)
                .returns(expectedContent, PagedResponse::getContent)
                .returns(Boolean.TRUE, PagedResponse::isLast);
    }

    private CreateArticleRequest prepareArticle(Long publishDate) {
        CreateArticleRequest createArticleRequest = new CreateArticleRequest();
        createArticleRequest.setTitle(ARTICLE_TITLE);
        createArticleRequest.setAuthorId(AUTHOR_ID);
        createArticleRequest.setContent(ARTICLE_CONTENT);
        createArticleRequest.setPublishDate(publishDate);
        return createArticleRequest;
    }
}