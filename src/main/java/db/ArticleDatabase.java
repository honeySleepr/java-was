package db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Article;

public class ArticleDatabase {
    private static final Logger log = LoggerFactory.getLogger(UserDatabase.class);
    private static Map<String, Article> articles = new HashMap<>(Map.of(
        "bc", new Article("bc", "내가 미션 6단계라니~~~~")
    ));

    public static Optional<Article> addArticle(Article article) {
        if (articles.containsKey(article.getUserId())) {
            return Optional.empty();
        }
        articles.put(article.getUserId(), article);
        log.debug("article: {}", article);
        return Optional.ofNullable(article);
    }

    public static Optional<Article> findByArticleId(String articleId) {
        return Optional.ofNullable(articles.get(articleId));
    }

    public static Collection<Article> findAll() {
        return articles.values();
    }
}
