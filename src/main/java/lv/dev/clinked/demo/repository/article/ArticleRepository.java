package lv.dev.clinked.demo.repository.article;

import lv.dev.clinked.demo.model.article.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    Integer countArticlesByPublishDateAfterAndPublishDateBefore(Date publishDateAfter, Date publishDateBefore);

}
