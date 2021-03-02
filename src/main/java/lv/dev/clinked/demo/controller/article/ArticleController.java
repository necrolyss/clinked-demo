package lv.dev.clinked.demo.controller.article;

import lv.dev.clinked.demo.model.article.Article;
import lv.dev.clinked.demo.payloads.ApiResponse;
import lv.dev.clinked.demo.payloads.PagedResponse;
import lv.dev.clinked.demo.payloads.article.ArticleSummary;
import lv.dev.clinked.demo.payloads.article.CreateArticleRequest;
import lv.dev.clinked.demo.service.article.ArticleService;
import lv.dev.clinked.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractResourceBasedMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private static final String ARTICLE_CREATE_SUCCESS_MESSAGE = "article.create.success";
    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final String DEFAULT_PAGE_SIZE = "5";

    private final ArticleService articleService;
    private final UserService userService;
    private final AbstractResourceBasedMessageSource resourceBundleMessageSource;
    private final Integer maxPageSize;

    @Autowired
    ArticleController(ArticleService articleService, UserService userService,
                      AbstractResourceBasedMessageSource resourceBundleMessageSource,
                      @Value("${app.articles.max-page-size}") Integer maxPageSize) {
        this.articleService = articleService;
        this.userService = userService;
        this.resourceBundleMessageSource = resourceBundleMessageSource;
        this.maxPageSize = maxPageSize;
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> createArticle(@Valid @RequestBody CreateArticleRequest articleRequest) {
        articleService.persist(articleOf(articleRequest));
        String successMessage = articleCreationSuccessMessage();
        return ResponseEntity.ok(new ApiResponse(true, successMessage));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_USER')")
    public PagedResponse<ArticleSummary> listArticles(@RequestParam(value = "page", defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,
                                                      @RequestParam(value = "size", defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {
        Page<ArticleSummary> articleSummaries = articlePage(pageNumber, pageSize);
        return new PagedResponse<>(articleSummaries.getContent(),
                articleSummaries.getNumber(),
                articleSummaries.getSize(),
                articleSummaries.getTotalElements(),
                articleSummaries.getTotalPages(),
                articleSummaries.isLast()
        );
    }

    private Article articleOf(CreateArticleRequest articleRequest) {
        Article article = new Article();
        article.setTitle(articleRequest.getTitle());
        article.setContent(articleRequest.getContent());
        article.setPublishDate(new Date(articleRequest.getPublishDate()));
        article.setAuthor(userService.userById(articleRequest.getAuthorId()));
        return article;
    }

    private String articleCreationSuccessMessage() {
        return resourceBundleMessageSource.getMessage(ARTICLE_CREATE_SUCCESS_MESSAGE, null, LocaleContextHolder.getLocale());
    }

    private Page<ArticleSummary> articlePage(int pageNumber, int pageSize) {
        int size = Math.min(pageSize, maxPageSize);
        Pageable pageRequest = PageRequest.of(pageNumber, size);
        return articleService.articleSummariesOf(pageRequest);
    }
}
