package webserver.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

import db.ArticleDatabase;
import model.Article;
import util.HttpRequestUtils;
import webserver.http.Request;
import webserver.http.Response;

public class ArticleCreateController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(ArticleCreateController.class);

    @Override
    public void process(Request request, Response response) throws IOException {
        Optional.ofNullable(request.getRequestHeader().get("Cookie"))
            .map(cookie -> HttpRequestUtils.parseCookies(cookie).get("sessionId"))
            .filter(sessionId -> !Strings.isNullOrEmpty(sessionId))
            .ifPresentOrElse(
                value -> createArticle(request, response),
                () -> response.response302Header("/user/login.html")
            );
    }

    private void createArticle(Request request, Response response) {
        String cookie = request.getRequestHeader().get("Cookie");
        String userId = HttpRequestUtils.parseCookies(cookie).get("sessionId");
        Map<String, String> articleContent = HttpRequestUtils.parseQueryString(request.getRequestBody().orElseThrow());
        Article article = new Article(userId, articleContent.get("title"));
        ArticleDatabase.addArticle(article);
        response.response302Header("/index");
    }
}
